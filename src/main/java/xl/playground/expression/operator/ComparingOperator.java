package xl.playground.expression.operator;

/**
 * Created by xl on 3/2/16.
 */
public enum ComparingOperator implements BinaryOperator<Comparable<Object>, Object, Boolean> {
    LESSEQUAL {
        @Override
        public Boolean apply(Comparable<Object> left, Object right) {
            return left.compareTo(right) <= 0;
        }
    },
    GREATEREQUAL {
        @Override
        public Boolean apply(Comparable<Object> left, Object right) {
            return left.compareTo(right) >= 0;
        }
    },
    LESS {
        @Override
        public Boolean apply(Comparable<Object> left, Object right) {
            return left.compareTo(right) < 0;
        }
    },
    GREATER {
        @Override
        public Boolean apply(Comparable<Object>  left, Object right) {
            return left.compareTo(right) > 0;
        }
    };

    public static ComparingOperator get(String opString) {
        switch (opString) {
            case "<=": return LESSEQUAL;
            case ">=": return GREATEREQUAL;
            case "<": return LESS;
            case ">": return GREATER;
            default: throw new IllegalStateException("unknown operator " + opString);
        }
    }
}
