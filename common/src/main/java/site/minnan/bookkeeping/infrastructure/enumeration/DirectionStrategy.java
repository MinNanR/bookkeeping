package site.minnan.bookkeeping.infrastructure.enumeration;

import site.minnan.bookkeeping.infrastructure.interfaces.Statistics;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface DirectionStrategy {

    Consumer<BigDecimal> calculate(Statistics statistics);

    BiConsumer<BigDecimal, BigDecimal> correct(Statistics statistics);
}
