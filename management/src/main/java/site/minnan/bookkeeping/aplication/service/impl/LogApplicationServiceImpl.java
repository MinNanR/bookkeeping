package site.minnan.bookkeeping.aplication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.OperationLog;
import site.minnan.bookkeeping.domain.repository.OperationLogRepository;
import site.minnan.bookkeeping.aplication.service.LogApplicationService;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.infrastructure.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class LogApplicationServiceImpl implements LogApplicationService {

    @Autowired
    private OperationLogRepository operationLogRepository;

    /**
     * 添加日志
     *
     * @param operateLog
     * @param request
     */
    @Override
    public void addLog(OperateLog operateLog, HttpServletRequest request) {
        String ip = WebUtil.getIpAddr(request);
        String requestURI = request.getRequestURI();
        Operation operation = operateLog.operation();
        String module = operateLog.module();
        String content = operateLog.content();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OperationLog operationLog = null;
        if (principal instanceof JwtUser) {
            JwtUser user = (JwtUser) principal;
            operationLog = OperationLog.of(user.getId(), user.getUsername(), operation.name(), module,
                    content, requestURI, ip, Timestamp.from(Instant.now()));
        } else {
            String username = principal instanceof String ? (String) principal : "匿名用户";
            operationLog = OperationLog.of(null, username, operation.name(), module,
                    content, requestURI, ip, Timestamp.from(Instant.now()));
        }
        operationLogRepository.save(operationLog);
    }
}
