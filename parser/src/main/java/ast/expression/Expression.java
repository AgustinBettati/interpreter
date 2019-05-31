package ast.expression;

import ast.TraceableNode;
import token.InputRange;

public abstract class Expression extends TraceableNode {

    public Expression(InputRange range) {
        super(range);
    }

}
