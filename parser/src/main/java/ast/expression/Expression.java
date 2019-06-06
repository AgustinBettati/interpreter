package ast.expression;

import ast.ExpressionVisitor;
import ast.TraceableNode;
import token.InputRange;

public abstract class Expression extends TraceableNode {

    public Expression(InputRange range) {
        super(range);
    }
    public abstract void accept(ExpressionVisitor visitor);

}
