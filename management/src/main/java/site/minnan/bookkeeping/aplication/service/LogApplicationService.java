package site.minnan.bookkeeping.aplication.service;

import site.minnan.bookkeeping.domain.vo.log.GetLogListVO;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.userinterface.dto.DownloadLogDTO;
import site.minnan.bookkeeping.userinterface.dto.GetLogListDTO;

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
    GetLogListVO getLogList(GetLogListDTO dto);

    /**
     * 下载日志
     *
     * @param dto
     * @param outputStream
     */
    void downloadLogList(DownloadLogDTO dto, OutputStream outputStream);
}
