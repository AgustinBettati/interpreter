package ast.statement;

import ast.ASTNode;
import ast.ASTVisitor;
import ast.expression.Expression;
import ast.statement.Statement;
import token.InputRange;

import java.util.Collections;
import java.util.List;

public class PrintStatement extends Statement {

    private final Expression expressionToPrint;

    public PrintStatement(InputRange range, Expression expressionToPrint) {
        super(range);
        this.expressionToPrint = expressionToPrint;
    }

    @Override
    public void accept(ASTVisitor visitor) {

    }

    @Override
    public List<ASTNode> getChildren() {
        return Collections.singletonList(expressionToPrint);
    }

    public Expression getExpressionToPrint() {
        return expressionToPrint;
    }
}
