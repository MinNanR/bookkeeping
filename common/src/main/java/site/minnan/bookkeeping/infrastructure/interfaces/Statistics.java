package site.minnan.bookkeeping.infrastructure.interfaces;

import java.math.BigDecimal;

public interface Statistics {

    /**
     * 统计项目余额
     *
     * @return
     */
    BigDecimal getBalance();

    /**
     * 获取总收入
     */
    BigDecimal getTotalIncome();

    /**
     * 获取总支出
     *
     * @return
     */
    BigDecimal getTotalExpense();

    /**
     * 注入统计后的余额
     *
     * @param balance
     */
    void setBalance(BigDecimal balance);

    /**
     * 注入统计后的总收入
     *
     * @param income
     */
    void setTotalIncome(BigDecimal income);

    /**
     * 注入统计后的总支出
     *
     * @param expense
     */
    void setTotalExpense(BigDecimal expense);
}
