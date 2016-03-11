package xl.playground.expression.operator;

/**
 * Created by xl on 3/2/16.
 */
public enum ArithmeticOperator implements BinaryOperator<Number, Number, Number> {
    ADD {
        @Override
        public Number apply(Number left, Number right) {
            if(left instanceof Float || right instanceof Float) return new Float(left.floatValue() + right.floatValue());
            else return new Integer(left.intValue() + right.intValue());
        }
    },
    SUBTRACT {
        @Override
        public Number apply(Number left, Number right) {
            if(left instanceof Float || right instanceof Float) return new Float(left.floatValue() - right.floatValue());
            else return new Integer(left.intValue() - right.intValue());
        }
    },
    MULTIPLY {
        @Override
        public Number apply(Number left, Number right) {
            if(left instanceof Float || right instanceof Float) return new Float(left.floatValue() * right.floatValue());
            else return new Integer(left.intValue() * right.intValue());
        }
    },
    DIVIDE {
        @Override
        public Number apply(Number left, Number right) {
            if(left instanceof Float || right instanceof Float) return new Float(left.floatValue() / right.floatValue());
            else return new Integer(left.intValue() / right.intValue());
        }
    },
    REMAINDER {
        @Override
        public Number apply(Number left, Number right) {
            if(left instanceof Float || right instanceof Float) return new Float(left.floatValue() % right.floatValue());
            else return new Integer(left.intValue() % right.intValue());
        }
    };

    public static ArithmeticOperator get(String opString) {
        switch (opString) {
            case "+": return ADD;
            case "-": return SUBTRACT;
            case "*": return MULTIPLY;
            case "/": return DIVIDE;
            case "%": return REMAINDER;
            default: throw new IllegalStateException("unknown operator " + opString);
        }
    }
}
