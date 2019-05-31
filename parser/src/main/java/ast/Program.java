package ast;

import ast.statement.Statement;
import token.RealInputRange;

import java.util.ArrayList;
import java.util.List;

public class Program extends TraceableNode {

    private final List<Statement> statements;

    public Program(List<Statement> statements) {
        super(new RealInputRange()); //TODO definir bien el input range
        this.statements = statements;
    }

    @Override
    public void accept(ASTVisitor visitor) {

    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<ASTNode>(statements);
    }

    public List<Statement> getStatements() {
        return new ArrayList<>(statements);
    }
}
