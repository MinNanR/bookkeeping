package site.minnan.bookkeeping.domain.aggreates;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 流水项
 */
public abstract class Journal {

    public abstract Integer getId();

    public abstract Integer getWarehouseId();

    public abstract BigDecimal getAmount();

    public abstract Timestamp getCreateTime();

    public abstract BigDecimal calculate();
}
