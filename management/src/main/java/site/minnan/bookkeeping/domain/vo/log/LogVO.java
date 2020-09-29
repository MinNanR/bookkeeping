package site.minnan.bookkeeping.domain.vo.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import site.minnan.bookkeeping.domain.aggreates.Log;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class LogVO {

    private Integer userId;

    private String username;

    private String operation;

    private String module;

    private String operateContent;

    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    public LogVO(Log log){
        this(log.getUserId(),
                log.getUsername(),
                log.getOperation(),
                log.getModule(),
                log.getOperateContent(),
                log.getIp(),
                log.getCreateTime());
    }

}
