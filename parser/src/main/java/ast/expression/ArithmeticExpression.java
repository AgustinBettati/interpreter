package ast.expression;

import ast.ASTNode;
import ast.ASTVisitor;
import ast.ArithmeticOperation;
import ast.ExpressionVisitor;
import token.InputRange;

import java.util.Arrays;
import java.util.List;

public class ArithmeticExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final ArithmeticOperation operation;

    public ArithmeticExpression(InputRange range, Expression left, Expression right, ArithmeticOperation operation) {
        super(range);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return Arrays.asList(left,right);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public ArithmeticOperation getOperation() {
        return operation;
    }
}
