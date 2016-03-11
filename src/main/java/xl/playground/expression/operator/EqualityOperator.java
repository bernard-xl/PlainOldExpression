package xl.playground.expression.operator;

/**
 * created by xl on 3/2/16.
 */
public enum EqualityOperator implements BinaryOperator<Object, Object, Boolean> {
    EQUAL {
        @Override
        public Boolean apply(Object left, Object right) {
            return left.equals(right);
        }
    },
    NOTEQUAL {
        @Override
        public Boolean apply(Object left, Object right) {
            return !left.equals(right);
        }
    };

    public static EqualityOperator get(String opString) {
        switch (opString) {
            case "==": return EQUAL;
            case "!=": return NOTEQUAL;
            default: throw new IllegalStateException("unknown operator " + opString);
        }
    }
}
