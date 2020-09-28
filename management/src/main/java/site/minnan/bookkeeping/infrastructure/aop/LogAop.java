package site.minnan.bookkeeping.infrastructure.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.minnan.bookkeeping.aplication.service.LogApplicationService;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.userinterface.response.ResponseCode;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@Aspect
public class LogAop {

    @Autowired
    private LogApplicationService logApplicationService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("execution(public * site.minnan.bookkeeping.userinterface.facade..*..*(..))")
    public void controllerLog() {
    }

    @Around(value = "controllerLog()")
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long time = System.currentTimeMillis();
        Object[] args = proceedingJoinPoint.getArgs();
        List<String> argStringList = Arrays.stream(args).map(Object::toString).collect(Collectors.toList());
        String argsString = objectMapper.writeValueAsString(argStringList);
        String methodFullName = proceedingJoinPoint.getTarget().getClass().getName()
                + "." + proceedingJoinPoint.getSignature().getName();
        log.info("controller调用{}，参数：{}", methodFullName, argsString);
        Object retValue = proceedingJoinPoint.proceed();
        time = System.currentTimeMillis() - time;
        String responseString = objectMapper.writeValueAsString(retValue);
        log.info("controller调用{}完成，返回数据:{}，用时{}ms", methodFullName, responseString, time);
        //获取操作类型
        if (retValue instanceof ResponseEntity){
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) retValue;
            if (ResponseCode.SUCCESS.code().equals(responseEntity.getCode())) {
                MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
                Optional<OperateLog> operateLog =
                        Optional.ofNullable(methodSignature.getMethod().getAnnotation(OperateLog.class));
                operateLog.ifPresent(log -> {
                    logApplicationService.addLog(log, request);
                });
            }
        }
        return retValue;
    }

}
