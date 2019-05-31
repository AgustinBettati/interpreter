package ast;

import java.util.List;

public interface ASTNode {
    void accept(ASTVisitor visitor);
    List<ASTNode> getChildren();
}
