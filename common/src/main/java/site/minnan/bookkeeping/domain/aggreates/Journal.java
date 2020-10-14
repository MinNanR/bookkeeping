package site.minnan.bookkeeping.domain.aggreates;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 流水项
 */
public abstract class Journal {

    /**
     * 获取id
     * @return
     */
    public abstract Integer getId();

    /**
     * 获取金库id
     * @return
     */
    public abstract Integer getWarehouseId();

    /**
     * 获取金额
     * @return
     */
    public abstract BigDecimal getAmount();

    /**
     * 获取创建时间
     * @return
     */
    public abstract Timestamp getCreateTime();

    /**
     * 计算金额变化，支出时返回复数，收入时返回正数
     * @return
     */
    public abstract BigDecimal calculate();

    /**
     * 修正记录中的数值
     * @param newAmount
     * @return
     */
    public abstract BigDecimal correct(BigDecimal newAmount);

}
