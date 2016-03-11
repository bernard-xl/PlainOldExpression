package xl.playground.expression.parse;

import xl.playground.expression.eval.*;
import xl.playground.expression.operator.ArithmeticOperator;
import xl.playground.expression.operator.ComparingOperator;
import xl.playground.expression.operator.EqualityOperator;
import xl.playground.expression.operator.LogicalOperator;
import xl.playground.expression.parse.ExpressionBaseVisitor;
import xl.playground.expression.parse.ExpressionParser;

/**
 * Created by xl on 3/2/16.
 */
public class ExpressionParsingVisitor extends ExpressionBaseVisitor<Evaluatable> {

    private Context context;

    public ExpressionParsingVisitor() {
        this.context = new EmptyContext();
    }

    public ExpressionParsingVisitor(Context context) {
        this.context = context;
    }

    @Override
    public Evaluatable visitParenExpression(ExpressionParser.ParenExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Evaluatable visitFieldAccess(ExpressionParser.FieldAccessContext ctx) {
        Evaluatable left = visit(ctx.left);
        String fieldName = ctx.Identifier().toString();
        return new ReferenceExpression(left, fieldName);
    }

    @Override
    public Evaluatable visitLogical(ExpressionParser.LogicalContext ctx) {
        Evaluatable left = visit(ctx.left);
        Evaluatable right = visit(ctx.right);
        String operator = ctx.op.getText();
        return new LogicalExpression(left, right, LogicalOperator.get(operator));
    }

    @Override
    public Evaluatable visitEquality(ExpressionParser.EqualityContext ctx) {
        Evaluatable left = visit(ctx.left);
        Evaluatable right = visit(ctx.right);
        String operator = ctx.op.getText();
        return new EqualityExpression(left, right, EqualityOperator.get(operator));
    }

    @Override
    public Evaluatable visitComparison(ExpressionParser.ComparisonContext ctx) {
        Evaluatable left = visit(ctx.left);
        Evaluatable right = visit(ctx.right);
        String operator = ctx.op.getText();
        return new ComparisonExpression(left, right, ComparingOperator.get(operator));
    }

    @Override
    public Evaluatable visitIndexAccess(ExpressionParser.IndexAccessContext ctx) {
        Evaluatable left = visit(ctx.left);
        Evaluatable index = visit(ctx.index);
        return new IndexExpression(left, index);
    }

    @Override
    public Evaluatable visitReference(ExpressionParser.ReferenceContext ctx) {
        String refName = ctx.Identifier().toString();
        Object resolved = context.get(refName).orElseThrow(
                () -> new IllegalStateException("unable to resolve reference '" + refName + "'"));
        return new ValueExpression(resolved);
    }

    @Override
    public Evaluatable visitArithmetic1(ExpressionParser.Arithmetic1Context ctx) {
        Evaluatable left = visit(ctx.left);
        Evaluatable right = visit(ctx.right);
        String operator = ctx.op.getText();
        return new ArithmeticExpression(left, right, ArithmeticOperator.get(operator));
    }

    @Override
    public Evaluatable visitArithmetic2(ExpressionParser.Arithmetic2Context ctx) {
        Evaluatable left = visit(ctx.left);
        Evaluatable right = visit(ctx.right);
        String operator = ctx.op.getText();
        return new ArithmeticExpression(left, right, ArithmeticOperator.get(operator));
    }

    @Override
    public Evaluatable visitIntLiteral(ExpressionParser.IntLiteralContext ctx) {
        return new ValueExpression(Integer.parseInt(ctx.getText()));
    }

    @Override
    public Evaluatable visitFloatLiteral(ExpressionParser.FloatLiteralContext ctx) {
        return new ValueExpression(Float.parseFloat(ctx.getText()));
    }

    @Override
    public Evaluatable visitStringLiteral(ExpressionParser.StringLiteralContext ctx) {
        String textIncludeQuotes = ctx.getText();
        return new ValueExpression(textIncludeQuotes.substring(1, textIncludeQuotes.length() - 1));
    }

    @Override
    public Evaluatable visitBooleanLiteral(ExpressionParser.BooleanLiteralContext ctx) {
        return new ValueExpression(Boolean.valueOf(ctx.getText()));
    }

    @Override
    public Evaluatable visitNullLiteral(ExpressionParser.NullLiteralContext ctx) {
        return new ValueExpression(null);
    }
}
