package xl.playgroud.expression;

import org.junit.Test;
import xl.playground.expression.Expression;
import xl.playground.expression.eval.Context;
import xl.playground.expression.eval.EnvironmentContext;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by xl on 3/2/16.
 */
public class ExpressionTest {

    private static final Context STRUCTURE_CONTEXT = new EnvironmentContext(
                    new Structure(2333, "(No name is given)",
                    new Structure(6666, "Another structure", null)));

    private static final Context INDEX_CONTEXT = new EnvironmentContext(null)
            .put("numberArray", new int[] {11, 22, 33})
            .put("objectList", Arrays.asList(new Structure(2333, "Structure in list", null)))
            .put("stringMap", new HashMap<String, String>() {{ put("First", "1st"); }});

    @Test
    public void testIntegerArithmetic() {
        assert eval("1 + 1 * 2", Integer.class) == 3;
    }

    @Test
    public void testIntegerArithmeticWithParenthesis() {
        assert eval("(1 + 1) * 2", Integer.class) == 4;
    }

    @Test
    public void testFloatArithmetic() {
        assert eval("1.2 + 1.8 * 2.0", Float.class) == 4.8f;
    }

    @Test
    public void testFloatArithmeticWithExponentialPart() {
        assert eval("3e2", Float.class) == 3e2;
    }

    @Test
    public void testMixedArithmetic() {
        assert eval("3.2 + 2 * 2", Float.class) == 7.2f;
    }

    @Test
    public void testComparison() {
        assert eval("1 + 2 > 2", Boolean.class) == true;
    }

    @Test
    public void testLogical() {
        assert eval("(true || false) && true", Boolean.class) == true;
    }

    @Test
    public void testEquality() {
        assert eval(" 'hello world' == 'hello world' ", Boolean.class) == true;
    }

    @Test
    public void testThisReference() {
        assert evalWithContext("this.name", STRUCTURE_CONTEXT, String.class).equals("(No name is given)");
    }

    @Test
    public void testImplicitThisReference() {
        assert evalWithContext("id", STRUCTURE_CONTEXT, Integer.class) == 2333;
    }

    @Test
    public void testObjectReference() {
        assert evalWithContext("successor", STRUCTURE_CONTEXT, Structure.class) != null;
    }

    @Test
    public void testNestedReference() {
        assert evalWithContext("this.successor.name", STRUCTURE_CONTEXT, String.class).equals("Another structure");
    }

    @Test
    public void testNumericIndexAccess() {
        assert evalWithContext("numberArray[0] + objectList[0].id", INDEX_CONTEXT, Integer.class) == 2344;
    }

    @Test
    public void testStringIndexAccess() {
        assert evalWithContext("stringMap['First'] == '1st'", INDEX_CONTEXT, Boolean.class) == true;
    }

    private <T> T evalWithContext(String expression, Context context, Class<T> clazz) {
        Expression expr = new Expression(expression);
        return expr.evaluate(clazz, context);
    }

    private <T> T eval(String expression, Class<T> clazz) {
        Expression expr = new Expression(expression);
        return expr.evaluate(clazz);
    }

    public static class Structure {
        int id;
        String name;
        Structure successor;

        public Structure(int id, String name, Structure succesor) {
            this.id = id;
            this.name = name;
            this.successor = succesor;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Structure getSuccessor() {
            return successor;
        }

        @Override
        public String toString() {
            return "Structure{" + "id=" + id + ", name='" + name + '\'' + ", successor=" + successor + '}';
        }
    }
}
