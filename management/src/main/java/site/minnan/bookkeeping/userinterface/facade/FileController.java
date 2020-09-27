package site.minnan.bookkeeping.userinterface.facade;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.minnan.bookkeeping.aplication.service.LogApplicationService;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.userinterface.dto.log.DownloadLogDTO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/management/file")
@Slf4j
public class FileController {

    @Autowired
    private LogApplicationService logApplicationService;

    @OperateLog(operation = Operation.DOWNLOAD, module = "日志", content = "下载日志")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/downloadLog")
    public void downloadLog(DownloadLogDTO dto, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            String fileName = StrUtil.format("log_{}", DateUtil.format(DateUtil.date(), "yyyy-MM-dd_HH-mm"));
            response.setHeader("Content-Disposition", StrUtil.format("attachment;filename={}.xls", fileName));
            outputStream = response.getOutputStream();
            logApplicationService.downloadLogList(dto, outputStream);
        } catch (IOException e) {
            log.error("下载日志IO异常", e);
            throw e;
        } finally {
            if (outputStream != null) {
                IoUtil.close(outputStream);
            }
        }
    }
}
