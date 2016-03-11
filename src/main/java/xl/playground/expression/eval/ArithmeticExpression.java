package xl.playground.expression.eval;

import xl.playground.expression.operator.BinaryOperator;

/**
 * Created by xl on 3/2/16.
 */
public class ArithmeticExpression extends BinaryExpression<Number, Number, Number> {

    public ArithmeticExpression(Evaluatable left, Evaluatable right, BinaryOperator<Number, Number, Number> operator) {
        super(left, right, operator);
    }

    @Override
    public <T> T evaluate(Class<T> clazz) {
        return clazz.cast(operator.apply(left.evaluate(Number.class), right.evaluate(Number.class)));
    }

    @Override
    public String toString() {
        return "ArithmeticExpression(" + left + ", " + operator + ", " + right + ")";
    }
}
