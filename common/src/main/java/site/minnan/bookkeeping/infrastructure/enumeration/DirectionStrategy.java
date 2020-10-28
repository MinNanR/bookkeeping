package site.minnan.bookkeeping.infrastructure.enumeration;

import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface DirectionStrategy {

    Function<BigDecimal, BigDecimal> calculate(Warehouse warehouse);

    Consumer<BigDecimal> calculate(Ledger ledger);

    BiFunction<BigDecimal, BigDecimal, BigDecimal> correct(Warehouse warehouse);

    BiConsumer<BigDecimal, BigDecimal> correct(Ledger ledger);
}
