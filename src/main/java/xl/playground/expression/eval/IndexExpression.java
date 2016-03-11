package xl.playground.expression.eval;

import xl.playground.expression.utils.ObjectAccessUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created by xl on 3/3/16.
 */
public class IndexExpression implements Evaluatable {

    private Evaluatable collection;

    private Evaluatable index;

    public IndexExpression(Evaluatable collection, Evaluatable index) {
        this.collection = collection;
        this.index = index;
    }

    @Override
    public <T> T evaluate(Class<T> clazz) {
        Object evaluated = collection.evaluate(Object.class);
        Class<?> evaluatedClass = evaluated.getClass();

        return clazz.cast(evaluatedClass.isArray()? accessArray(evaluated, index) : accessCollection(evaluated, index));
    }

    private Object accessArray(Object evaluated, Evaluatable index) {
        try {
            int numericIndex = index.evaluate(Integer.class);
            return Array.get(evaluated, numericIndex);
        } catch (ClassCastException e) {
            throw new IllegalStateException("access array with non-integer index");
        }
    }

    private Object accessCollection(Object evaluated, Evaluatable index) {
        Class<?> evaluatedClass = evaluated.getClass();
        Optional<Method> listMethod = ObjectAccessUtils.findMethod(evaluated, "get", Integer.TYPE);
        if(listMethod.isPresent()) {
            try {
                Method method = listMethod.get();
                int numericIndex = index.evaluate(Integer.class);
                return method.invoke(evaluated, numericIndex);
            } catch (ClassCastException e) {
                throw new IllegalStateException("access list with non-integer index");
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new IllegalStateException("unable to access collection", e);
            }
        }

        Optional<Method> otherMethod = ObjectAccessUtils.findMethod(evaluated, "get", Object.class);
        if(otherMethod.isPresent()) {
            try {
                Method method = otherMethod.get();
                Object objectIndex = index.evaluate(Object.class);
                return method.invoke(evaluated, objectIndex);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new IllegalStateException("unable to access collection", e);
            }
        }

        throw new IllegalStateException("index access on non-collection object");
    }
}
