package xl.playground.expression.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xl on 3/4/16.
 */
@SuppressWarnings("unchecked")
public class ObjectAccessUtils {

    private static Map<FieldIdentifier, Field> fields = new ConcurrentHashMap<>();

    private static Map<MethodIdentifier, Method> methods = new ConcurrentHashMap<>();

    public static <T> T getFieldValue(Object object, String fieldName) {
        try {
            Field field = findField(object, fieldName).orElseThrow(() -> new IllegalStateException("unable to find the field"));
            return (T) field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException("unable to access the field", e);
        }
    }

    public static Optional<Field> findField(Object object, String fieldName) {
        FieldIdentifier searchingKey = new FieldIdentifier(object.getClass(), fieldName);
        Field field = fields.computeIfAbsent(searchingKey, ObjectAccessUtils::findDeclaredField);
        return Optional.ofNullable(field);
    }

    public static Optional<Method> findMethod(Object object, String methodName, Class<?>...argsType) {
        MethodIdentifier searchingKey = new MethodIdentifier(object.getClass(), methodName, argsType);
        Method method = methods.computeIfAbsent(searchingKey, ObjectAccessUtils::findDeclaredMethod);
        return Optional.ofNullable(method);
    }

    private static Method findDeclaredMethod(MethodIdentifier mi) {
        Class<?> searchType = mi.getClazz();
        String methodName = mi.getMethodName();
        Class<?>[] paramsType = mi.getParamType();
        while(searchType != null) {
            for(Method method : searchType.getDeclaredMethods()) {
                if(method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), paramsType)) {
                    method.setAccessible(true);
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    private static Field findDeclaredField(FieldIdentifier fi) {
        Class<?> searchType = fi.getClazz();
        String fieldName = fi.getFieldName();
        while(searchType != null) {
            for(Field field : searchType.getDeclaredFields()) {
                if(field.getName().equals(fieldName)) {
                    if(!Modifier.isPublic(field.getModifiers())) field.setAccessible(true);
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    private static class FieldIdentifier {
        private Class<?> clazz;
        private String fieldName;

        public FieldIdentifier(Class<?> clazz, String fieldName) {
            this.clazz = clazz;
            this.fieldName = fieldName;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public String getFieldName() {
            return fieldName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FieldIdentifier that = (FieldIdentifier) o;

            if (!clazz.equals(that.clazz)) return false;
            return fieldName.equals(that.fieldName);

        }

        @Override
        public int hashCode() {
            int result = clazz.hashCode();
            result = 31 * result + fieldName.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return clazz + "::" + fieldName;
        }
    }

    private static class MethodIdentifier {
        private Class<?> clazz;
        private String methodName;
        private Class<?>[] paramType;

        public MethodIdentifier(Class<?> clazz, String methodName, Class<?>...paramType) {
            this.clazz = clazz;
            this.methodName = methodName;
            this.paramType = paramType;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public String getMethodName() {
            return methodName;
        }

        public Class<?>[] getParamType() {
            return paramType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodIdentifier that = (MethodIdentifier) o;

            if (!clazz.equals(that.clazz)) return false;
            if (!methodName.equals(that.methodName)) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(paramType, that.paramType);

        }

        @Override
        public int hashCode() {
            int result = clazz.hashCode();
            result = 31 * result + methodName.hashCode();
            result = 31 * result + Arrays.hashCode(paramType);
            return result;
        }

        @Override
        public String toString() {
            return clazz + "::" + methodName + "(" + Arrays.toString(paramType) + ")";
        }
    }
}
