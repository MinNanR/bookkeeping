package site.minnan.bookkeeping.infrastructure.enumeration;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum JournalDirection implements DirectionStrategy {

    EXPENSE("支出"){

        @Override
        public Function<BigDecimal, BigDecimal> calculate(){
            return (amount -> amount.abs().negate());
        }

        @Override
        public BiFunction<BigDecimal, BigDecimal, BigDecimal> correct() {
            return ((amount, newAmount) -> newAmount.subtract(amount));
        }
    }

    ,INCOME("收入"){

        @Override
        public Function<BigDecimal, BigDecimal> calculate(){
            return BigDecimal::abs;
        }

        @Override
        public BiFunction<BigDecimal, BigDecimal, BigDecimal> correct() {
            return BigDecimal::subtract;
        }
    };

    private final String direction;

    JournalDirection(String direction){
        this.direction = direction;
    }


}
