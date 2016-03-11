package xl.playground.expression.eval;

import xl.playground.expression.operator.BinaryOperator;

/**
 * Created by xl on 3/3/16.
 */
public class LogicalExpression extends BinaryExpression<Boolean, Boolean, Boolean> {

    public LogicalExpression(Evaluatable left, Evaluatable right, BinaryOperator<Boolean, Boolean, Boolean> operator) {
        super(left, right, operator);
    }

    @Override
    public <T> T evaluate(Class<T> clazz) {
        return clazz.cast(operator.apply(left.evaluate(Boolean.class), right.evaluate(Boolean.class)));
    }

    @Override
    public String toString() {
        return "LogicalExpression(" + left + ", " + operator + ", " + right + ")";
    }
}
