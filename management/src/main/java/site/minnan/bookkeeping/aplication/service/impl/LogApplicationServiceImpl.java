package site.minnan.bookkeeping.aplication.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Log;
import site.minnan.bookkeeping.domain.repository.LogRepository;
import site.minnan.bookkeeping.aplication.service.LogApplicationService;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.domain.vo.log.GetLogListVO;
import site.minnan.bookkeeping.domain.vo.log.LogVO;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.infrastructure.utils.WebUtil;
import site.minnan.bookkeeping.userinterface.dto.DownloadLogDTO;
import site.minnan.bookkeeping.userinterface.dto.GetLogListDTO;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogApplicationServiceImpl implements LogApplicationService {

    @Autowired
    private LogRepository logRepository;

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
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Log log = Log.of(user.getId(), user.getUsername(), operation.operationName(), module,
                content, requestURI, ip, Timestamp.from(Instant.now()));
        logRepository.save(log);
    }

    /**
     * 查询日志
     *
     * @param dto
     * @return
     */
    @Override
    public GetLogListVO getLogList(GetLogListDTO dto) {
        Optional<String> usernameOptional = Optional.ofNullable(dto.getUsername());
        Optional<String> operationOptional = Optional.ofNullable(dto.getOperation());
        Page<Log> logPage = logRepository.findAll((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            String username = StrUtil.format("%{}%", usernameOptional.orElse(""));
            conjunction.getExpressions().add(criteriaBuilder.like(root.get("username"), username));
            operationOptional.ifPresent(operation ->
                    conjunction.getExpressions().add(criteriaBuilder.equal(root.get("operation"), operation)));
            return conjunction;
        }, PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize(), Sort.by(Sort.Direction.DESC, "createTime")));

        List<LogVO> logVOList = logPage.get()
                .map(LogVO::new)
                .collect(Collectors.toList());
        return new GetLogListVO(logVOList, logPage.getTotalElements());
    }

    /**
     * 下载日志
     *
     * @param dto
     * @param outputStream
     */
    @Override
    public void downloadLogList(DownloadLogDTO dto, OutputStream outputStream) {
        Optional<String> usernameOptional = Optional.ofNullable(dto.getUsername());
        Optional<String> operationOptional = Optional.ofNullable(dto.getOperation());
        List<Log> logList = logRepository.findAll((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            String username = StrUtil.format("%{}%", usernameOptional.orElse(""));
            conjunction.getExpressions().add(criteriaBuilder.like(root.get("username"), username));
            operationOptional.ifPresent(operation ->
                    conjunction.getExpressions().add(criteriaBuilder.equal(root.get("operation"), operation)));
            return conjunction;
        });
        List<Map<String, Object>> data = logList.stream()
                .map(log -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("用户名", log.getUsername());
                    item.put("IP", log.getIp());
                    item.put("操作时间", log.getCreateTime());
                    item.put("模块", log.getModule());
                    item.put("操作", log.getOperation());
                    item.put("操作内容", log.getOperateContent());
                    return item;
                })
                .collect(Collectors.toList());
        ExcelWriter writer = new ExcelWriter();
        writer.write(data, true);
        writer.flush(outputStream, true);
        IoUtil.close(outputStream);
    }
}
