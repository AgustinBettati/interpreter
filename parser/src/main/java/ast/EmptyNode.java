package ast;

import java.util.ArrayList;
import java.util.List;

public class EmptyNode implements ASTNode {
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>();
    }
}
