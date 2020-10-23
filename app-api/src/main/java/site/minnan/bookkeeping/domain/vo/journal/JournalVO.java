package site.minnan.bookkeeping.domain.vo.journal;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import site.minnan.bookkeeping.domain.aggreates.Journal;

import java.math.BigDecimal;

@Data
public class JournalVO {

    /**
     * id值
     */
    private Integer id;

    /**
     * 金库名
     */
    private String warehouse;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 类型名称
     */
    private String journalType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 记录类型（收入或支出）
     */
    private String direction;

    public JournalVO(Journal journal, String warehouseName, String journalTypeName){
        this.id = journal.getId();
        this.warehouse = warehouseName;
        this.amount = journal.getAmount();
        this.createTime = DateUtil.format(journal.getCreateTime(), "yyyy-MM-dd HH:mm");
        this.journalType = journalTypeName;
        this.remark = journal.getRemark();
    }

}
