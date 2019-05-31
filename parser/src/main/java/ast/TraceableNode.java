package ast;

import token.InputRange;

public abstract class TraceableNode implements ASTNode {
    private final InputRange range;

    public TraceableNode(InputRange range) {
        this.range = range;
    }

    public InputRange getInputRange(){ return range;}
}
