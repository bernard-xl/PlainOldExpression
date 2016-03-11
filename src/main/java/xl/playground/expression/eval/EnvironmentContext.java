package xl.playground.expression.eval;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xl on 3/3/16.
 */
public class EnvironmentContext implements Context {

    private Object background;

    private Map<String, Object> decorations;

    public EnvironmentContext(Object background) {
        this.background = background;
        this.decorations = new ConcurrentHashMap<>();
    }

    public EnvironmentContext put(String name, Object item) {
        decorations.put(name, item);
        return this;
    }

    public EnvironmentContext remove(String name) {
        decorations.remove(name);
        return this;
    }

    public EnvironmentContext clear() {
        decorations.clear();
        return this;
    }

    @Override
    public Optional<Object> get(String name) {
        Object result = decorations.get(name);
        return result != null ? Optional.of(result) : tryResolveFromBackground(name);
    }

    private Optional<Object> tryResolveFromBackground(String name) {
        if(name.equals("this")) {
            return Optional.of(background);
        }

        try {
            Class<?> clazz = background.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return Optional.of(field.get(background));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return Optional.empty();
        }
    }
}
