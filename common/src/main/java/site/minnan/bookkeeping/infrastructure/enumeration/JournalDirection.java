package site.minnan.bookkeeping.infrastructure.enumeration;

import site.minnan.bookkeeping.domain.aggreates.JournalType;
import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.infrastructure.interfaces.Statistics;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public enum JournalDirection implements DirectionStrategy {

    EXPENSE("支出") {
        @Override
        public Consumer<BigDecimal> calculate(Statistics statistics) {
            return amount -> {
                statistics.setBalance(statistics.getBalance().subtract(amount));
                statistics.setTotalExpense(statistics.getTotalExpense().add(amount));
            };
        }

        @Override
        public BiConsumer<BigDecimal, BigDecimal> correct(Statistics statistics) {
            return (amount, newAmount) -> {
                statistics.setBalance(statistics.getBalance().add(amount).subtract(newAmount));
                statistics.setTotalExpense(statistics.getTotalExpense().subtract(amount).add(amount));
            };
        }

    },

    INCOME("收入") {
        @Override
        public Consumer<BigDecimal> calculate(Statistics statistics) {
            return amount -> {
                statistics.setBalance(statistics.getBalance().add(amount));
                statistics.setTotalIncome(statistics.getTotalIncome().add(amount));
            };
        }

        @Override
        public BiConsumer<BigDecimal, BigDecimal> correct(Statistics statistics) {
            return (amount, newAmount) -> {
                statistics.setBalance(statistics.getBalance().subtract(amount).add(newAmount));
                statistics.setTotalIncome(statistics.getTotalIncome().subtract(amount).add(newAmount));
            };
        }
    };

    private final String direction;

    JournalDirection(String direction) {
        this.direction = direction;
    }


}
