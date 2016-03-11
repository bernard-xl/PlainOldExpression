package xl.playground.expression.eval;

import xl.playground.expression.operator.BinaryOperator;

/**
 * Created by xl on 3/3/16.
 */
public abstract class BinaryExpression<Left, Right, Result> implements Evaluatable {
    protected Evaluatable left;
    protected Evaluatable right;
    protected BinaryOperator<Left, Right, Result> operator;

    public BinaryExpression(Evaluatable left, Evaluatable right, BinaryOperator<Left, Right, Result> operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}
