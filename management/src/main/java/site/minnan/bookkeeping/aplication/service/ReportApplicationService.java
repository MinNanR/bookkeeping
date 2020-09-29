package site.minnan.bookkeeping.aplication.service;

import site.minnan.bookkeeping.domain.vo.report.GetIncomeReportVO;
import site.minnan.bookkeeping.userinterface.dto.GetIncomeReportDTO;

public interface ReportApplicationService {

    GetIncomeReportVO getIncomeReport(GetIncomeReportDTO dto);
}
