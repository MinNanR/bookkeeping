package site.minnan.bookkeeping.infrastructure.enumeration;

import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.infrastructure.interfaces.Statistics;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface DirectionStrategy {

    Consumer<BigDecimal> calculate(Statistics warehouse);

    BiConsumer<BigDecimal, BigDecimal> correct(Statistics warehouse);
}
