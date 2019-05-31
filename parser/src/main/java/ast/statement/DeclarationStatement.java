package ast.statement;

import ast.*;
import ast.expression.Identifier;
import token.InputRange;
import java.util.Collections;
import java.util.List;

public class DeclarationStatement extends Statement {

    private final PrimitiveType type;
    private final Identifier identifier;

    public DeclarationStatement(InputRange range, PrimitiveType type, Identifier identifier) {
        super(range);
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public void accept(ASTVisitor visitor) {

    }

    @Override
    public List<ASTNode> getChildren() {
        return Collections.singletonList(identifier);
    }

    public PrimitiveType getType() {
        return type;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
