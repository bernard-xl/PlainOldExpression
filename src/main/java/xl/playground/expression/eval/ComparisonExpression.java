package xl.playground.expression.eval;

import xl.playground.expression.operator.BinaryOperator;

/**
 * Created by xl on 3/2/16.
 */
public class ComparisonExpression extends BinaryExpression<Comparable<Object>, Object, Boolean> {

    public ComparisonExpression(Evaluatable left, Evaluatable right, BinaryOperator<Comparable<Object>, Object, Boolean> operator) {
        super(left, right, operator);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T evaluate(Class<T> clazz) {
        return clazz.cast(operator.apply(left.evaluate(Comparable.class), right.evaluate(Object.class)));
    }

    @Override
    public String toString() {
        return "ComparingExpression(" + left + ", " + operator + ", " + right + ")";
    }
}
