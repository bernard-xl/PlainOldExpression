package xl.playground.expression.eval;

import java.util.Optional;

/**
 * Created by xl on 3/3/16.
 */
public interface Context {
    Optional<Object> get(String name);
}
