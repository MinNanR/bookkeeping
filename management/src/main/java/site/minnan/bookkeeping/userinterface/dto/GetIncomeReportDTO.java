package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GetIncomeReportDTO {

    @Max(message = "错误的时间维度", value = 3)
    @Min(message = "错误的时间维度", value = 0)
    private Integer reportType;

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer quarter;

}
