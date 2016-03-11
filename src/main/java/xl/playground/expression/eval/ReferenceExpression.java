package xl.playground.expression.eval;

import xl.playground.expression.utils.ObjectAccessUtils;

/**
 * Created by xl on 3/3/16.
 */
public class ReferenceExpression implements Evaluatable {

    private Evaluatable evaluatable;
    private String fieldName;

    public ReferenceExpression(Evaluatable evaluatable, String fieldName) {
        this.evaluatable = evaluatable;
        this.fieldName = fieldName;
    }

    @Override
    public <T> T evaluate(Class<T> clazz) {
        Object object = evaluatable.evaluate(Object.class);
        return clazz.cast(ObjectAccessUtils.getFieldValue(object, fieldName));
    }
}
