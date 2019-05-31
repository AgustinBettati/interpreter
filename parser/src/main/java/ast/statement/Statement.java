package ast.statement;

import ast.TraceableNode;
import token.InputRange;

public abstract class Statement extends TraceableNode {
    public Statement(InputRange range) {
        super(range);
    }
}
