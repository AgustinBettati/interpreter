import ast.ASTNode;
import errorhandler.ErrorHandler;
import parser.Parser;

public class RealInterpreter implements Interpreter{
    private Parser parser;

    public RealInterpreter(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void execute(String src, MessageEmitter emitter, ErrorHandler handler) {
        ExecutionVisitor visitor = new ExecutionVisitor(emitter, handler);
        final ASTNode astNode = parser.parse(src, handler);
        if(handler.conforms()) astNode.accept(visitor);
    }
}
