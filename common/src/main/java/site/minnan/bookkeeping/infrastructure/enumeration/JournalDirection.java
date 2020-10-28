package site.minnan.bookkeeping.infrastructure.enumeration;

import site.minnan.bookkeeping.domain.aggreates.JournalType;
import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public enum JournalDirection implements DirectionStrategy {

    EXPENSE("支出") {
        @Override
        public Function<BigDecimal, BigDecimal> calculate(Warehouse warehouse) {
            return amount -> warehouse.getBalance().subtract(amount);
        }

        @Override
        public Consumer<BigDecimal> calculate(Ledger ledger) {
            return amount -> ledger.setTotalExpense(ledger.getTotalExpense().add(amount));
        }

        @Override
        public BiFunction<BigDecimal, BigDecimal, BigDecimal> correct(Warehouse warehouse) {
            return (amount, newAmount) -> warehouse.getBalance().add(amount).subtract(newAmount);
        }

        @Override
        public BiConsumer<BigDecimal, BigDecimal> correct(Ledger ledger) {
            return (amount, newAmount) -> ledger.setTotalExpense(ledger.getTotalExpense().subtract(amount).add(newAmount));
        }
    },

    INCOME("收入") {
        @Override
        public Function<BigDecimal, BigDecimal> calculate(Warehouse warehouse) {
            return amount -> warehouse.getBalance().add(amount);
        }

        @Override
        public Consumer<BigDecimal> calculate(Ledger ledger) {
            return amount -> ledger.setTotalIncome(ledger.getTotalIncome().add(amount));
        }

        @Override
        public BiFunction<BigDecimal, BigDecimal, BigDecimal> correct(Warehouse warehouse) {
            return (amount, newAmount) -> warehouse.getBalance().subtract(amount).add(newAmount);
        }

        @Override
        public BiConsumer<BigDecimal, BigDecimal> correct(Ledger ledger) {
            return (amount, newAmount) -> ledger.setTotalIncome(ledger.getTotalIncome().subtract(amount).add(newAmount));
        }
    };

    private final String direction;

    JournalDirection(String direction) {
        this.direction = direction;
    }


}
