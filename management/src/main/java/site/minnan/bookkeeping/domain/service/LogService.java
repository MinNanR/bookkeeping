package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;

import javax.servlet.http.HttpServletRequest;

public interface LogService {

    /**
     * 添加日志
     * @param log
     */
    void addLog(OperateLog operateLog, HttpServletRequest request);
}
