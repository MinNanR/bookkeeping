package site.minnan.bookkeeping.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import site.minnan.bookkeeping.userinterface.response.ResponseCode;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

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
        log.error("Parameter Error,execute in : {},target : {}",ex.getParameter().getExecutable() ,ex.getBindingResult().getTarget());
        return ResponseEntity.fail(ResponseCode.INVALID_PARAM);
    }
}
