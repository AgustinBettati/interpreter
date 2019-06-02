package ast.expression;

import ast.ASTNode;
import ast.ASTVisitor;
import ast.Type;
import token.InputRange;

import java.util.ArrayList;
import java.util.List;

public class Literal extends Expression {

    private final Type type;
    private final Object value;

    public Literal(InputRange range, Type type, Object value) {
        super(range);
        this.type = type;
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {

    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>();
    }

    public Type getType() {
        return type;
    }

    public String getStringValue() {
        final String withQuotation = value.toString();
        return withQuotation.substring(1, withQuotation.length() - 1);
    }

    public Double getNumberValue() {
        return new Double(value.toString());
    }
}
