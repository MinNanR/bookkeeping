package site.minnan.bookkeeping.infrastructure.exception;

import cn.hutool.core.map.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import site.minnan.bookkeeping.userinterface.response.ResponseCode;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<?> handleAuthenticationException(HttpServletRequest request, Exception ex) {
        log.error("fail to login {}", ex.getMessage());
        return ResponseEntity.fail(ResponseCode.INVALID_USER);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Parameter Error,execute in : {},target : {}", ex.getParameter().getExecutable(),
                ex.getBindingResult().getTarget());
        List<Map<Object, Object>> details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> (FieldError) error)
                .map(error -> MapBuilder.create().put("field", error.getField()).put("message",
                        error.getDefaultMessage()).build())
                .collect(Collectors.toList());
        return ResponseEntity.fail(ResponseCode.INVALID_PARAM, MapBuilder.create().put("details", details).build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleUnknownException(Exception ex) {
        log.error("unknown error", ex);
        return ResponseEntity.fail(ResponseCode.UNKNOWN_ERROR);
    }
}
