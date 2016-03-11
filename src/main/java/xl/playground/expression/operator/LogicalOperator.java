package xl.playground.expression.operator;

/**
 * Created by xl on 3/3/16.
 */
public enum LogicalOperator implements BinaryOperator<Boolean, Boolean, Boolean> {
    OR {
        @Override
        public Boolean apply(Boolean left, Boolean right) {
            return left || right;
        }
    },
    AND {
        @Override
        public Boolean apply(Boolean left, Boolean right) {
            return left && right;
        }
    };

    public static LogicalOperator get(String opString) {
        switch (opString) {
            case "||": return OR;
            case "&&": return AND;
            default: throw new IllegalStateException("unknown operator " + opString);
        }
    }
}
