package ast.expression;

import ast.ASTNode;
import ast.ASTVisitor;
import ast.ExpressionVisitor;
import ast.Type;
import token.InputRange;

import java.util.ArrayList;
import java.util.List;

public class Literal extends Expression {

    private final Scalar scalar;


    public Literal(InputRange range, Type type, Object value) {
        super(range);
        this.scalar = new Scalar(type, value);
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
        return new ArrayList<>();
    }

    public Type getType() {
        return scalar.getType();
    }

    public Scalar getScalar() {
        return scalar;
    }

    public String getStringValue() {
        return scalar.getStringValue();
    }

    public Double getNumberValue() {
        return scalar.getNumberValue();
    }
}
