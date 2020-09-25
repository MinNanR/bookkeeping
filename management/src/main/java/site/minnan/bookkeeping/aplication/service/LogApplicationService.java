package site.minnan.bookkeeping.aplication.service;

import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;

import javax.servlet.http.HttpServletRequest;

public interface LogApplicationService {

    /**
     * 添加日志
     * @param operateLog
     */
    void addLog(OperateLog operateLog, HttpServletRequest request);
}
