package xl.playground.expression.eval;

/**
 * Created by xl on 3/2/16.
 */
public interface Evaluatable {
    public <T> T evaluate(Class<T> clazz);
}
