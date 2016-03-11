package xl.playground.expression.eval;

import java.util.Optional;

/**
 * Created by xl on 3/3/16.
 */
public class EmptyContext implements Context {

    @Override
    public Optional<Object> get(String name) {
        return Optional.empty();
    }
}
