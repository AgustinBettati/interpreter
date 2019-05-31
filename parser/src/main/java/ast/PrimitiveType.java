package ast;

import token.InputRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimitiveType extends TraceableNode {
    private final Type type;

    public PrimitiveType(InputRange range, Type type) {
        super(range);
        this.type = type;
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
}
