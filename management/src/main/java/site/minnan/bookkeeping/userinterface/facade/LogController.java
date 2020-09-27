package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.aplication.service.LogApplicationService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.log.LogVO;
import site.minnan.bookkeeping.userinterface.dto.log.GetLogListDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/management/log")
public class LogController {

    @Autowired
    LogApplicationService logApplicationService;

    /**
     * 查询日志
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getLogList")
    public ResponseEntity<QueryVO<LogVO>> getLogList(@RequestBody @Valid GetLogListDTO dto) {
        QueryVO<LogVO> vo = logApplicationService.getLogList(dto);
        return ResponseEntity.success(vo);
    }

}
