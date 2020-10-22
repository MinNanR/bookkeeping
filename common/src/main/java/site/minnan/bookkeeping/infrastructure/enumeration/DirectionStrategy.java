package site.minnan.bookkeeping.infrastructure.enumeration;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface DirectionStrategy {

    Function<BigDecimal, BigDecimal> calculate();

    BiFunction<BigDecimal, BigDecimal, BigDecimal> correct();
}
