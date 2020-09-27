package site.minnan.bookkeeping.aplication.service;

import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.log.LogVO;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.userinterface.dto.log.DownloadLogDTO;
import site.minnan.bookkeeping.userinterface.dto.log.GetLogListDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;

public interface LogApplicationService {

    /**
     * 添加日志
     *
     * @param operateLog
     */
    void addLog(OperateLog operateLog, HttpServletRequest request);

    /**
     * 查询日志
     *
     * @param dto
     * @return
     */
    QueryVO<LogVO> getLogList(GetLogListDTO dto);

    /**
     * 下载日志
     *
     * @param dto
     * @param outputStream
     */
    void downloadLogList(DownloadLogDTO dto, OutputStream outputStream);
}
