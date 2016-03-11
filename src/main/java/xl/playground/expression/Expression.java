package xl.playground.expression;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import xl.playground.expression.eval.Context;
import xl.playground.expression.eval.Evaluatable;
import xl.playground.expression.parse.ExpressionParsingVisitor;
import xl.playground.expression.parse.ExpressionLexer;
import xl.playground.expression.parse.ExpressionParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by xl on 3/3/16.
 */
public class Expression {

    private ExpressionLexer lexer;
    private ExpressionParser parser;

    public Expression(String expr) {
        lexer = new ExpressionLexer(new ANTLRInputStream(expr));
        parser = new ExpressionParser(new CommonTokenStream(lexer));
    }

    public Expression(InputStream expr) throws IOException {
        lexer = new ExpressionLexer(new ANTLRInputStream(expr));
        parser = new ExpressionParser(new CommonTokenStream(lexer));
    }

    public Expression(Reader expr) throws IOException {
        lexer = new ExpressionLexer(new ANTLRInputStream(expr));
        parser = new ExpressionParser(new CommonTokenStream(lexer));
    }

    public <T> T evaluate(Class<T> clazz) {
        Evaluatable result = new ExpressionParsingVisitor().visit(parser.expression());
        return result.evaluate(clazz);
    }

    public <T> T evaluate(Class<T> clazz, Context context) {
        Evaluatable result = new ExpressionParsingVisitor(context).visit(parser.expression());
        return result.evaluate(clazz);
    }
}
