import errorhandler.ErrorHandler;

public interface Interpreter {
    void execute(String src, MessageEmitter emitter, ErrorHandler handler);
}
