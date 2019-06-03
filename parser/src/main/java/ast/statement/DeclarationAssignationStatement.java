package ast.statement;

import ast.*;
import ast.expression.Expression;
import ast.expression.Identifier;
import token.InputRange;

import java.util.Arrays;
import java.util.List;

public class DeclarationAssignationStatement extends Statement {

    private final PrimitiveType type;
    private final Identifier identifier;
    private final Expression expression;

    public DeclarationAssignationStatement(InputRange range, PrimitiveType type, Identifier identifier, Expression expression) {
        super(range);
        this.type = type;
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return Arrays.asList(type, identifier, expression);
    }

    public PrimitiveType getType() {
        return type;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }
}
