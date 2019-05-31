package ast.expression;

import ast.ASTNode;
import ast.ASTVisitor;
import token.InputRange;

import java.util.ArrayList;
import java.util.List;

public class Identifier extends Expression {

    private final String name;

    public Identifier(InputRange range, String name) {
        super(range);
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {

    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>();
    }

    public String getName() {
        return name;
    }
}
