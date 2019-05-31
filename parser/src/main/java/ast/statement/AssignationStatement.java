package ast.statement;

import ast.*;
import ast.expression.Expression;
import ast.expression.Identifier;
import token.InputRange;

import java.util.Arrays;
import java.util.List;

public class AssignationStatement extends Statement {

    private final Identifier identifier;
    private final Expression expression;

    public AssignationStatement(InputRange range, Identifier identifier, Expression expression) {
        super(range);
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {

    }

    @Override
    public List<ASTNode> getChildren() {
        return Arrays.asList(identifier, expression);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }
}
