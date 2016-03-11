package xl.playground.expression.operator;

/**
 * Created by xl on 3/2/16.
 */
public interface BinaryOperator<Left, Right, Result> {
    Result apply(Left left, Right right);
}
