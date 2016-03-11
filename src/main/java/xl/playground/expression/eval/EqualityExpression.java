package xl.playground.expression.eval;

import xl.playground.expression.operator.BinaryOperator;


/**
 * Created by xl on 3/2/16.
 */
public class EqualityExpression extends BinaryExpression<Object, Object, Boolean> {


    public EqualityExpression(Evaluatable left, Evaluatable right, BinaryOperator<Object, Object, Boolean> operator) {
        super(left, right, operator);
    }

    @Override
    public <T> T evaluate(Class<T> clazz) {
        return clazz.cast(operator.apply(left.evaluate(Object.class), right.evaluate(Object.class)));
    }

    @Override
    public String toString() {
        return "EqualityExpression(" + left + ", " + operator + ", " + right + ")";
    }
}
