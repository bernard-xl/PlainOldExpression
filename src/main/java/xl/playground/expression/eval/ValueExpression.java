package xl.playground.expression.eval;

/**
 * Created by xl on 3/2/16.
 */
public class ValueExpression implements Evaluatable {

    private Object value;

    public ValueExpression(Object value) {
       this.value = value;
    }

    @Override
    public <T> T evaluate(Class<T> clazz) {
        return clazz.cast(value);
    }

    @Override
    public String toString() {
        return "ValueExpression(" + value + ")";
    }
}
