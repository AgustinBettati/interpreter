import ast.expression.Scalar;
import errorhandler.ErrorHandler;
import token.InputRange;

import java.util.HashMap;
import java.util.Map;

class ExecutionContext {
    private Map<String, Scalar> scopeVariables;
    private MessageEmitter emitter;
    private ErrorHandler handler;
    private boolean conforms;

    ExecutionContext(MessageEmitter emitter, ErrorHandler handler) {
        this.emitter = emitter;
        this.handler = handler;
        this.conforms = true;
        this.scopeVariables = new HashMap<>();
    }

    Scalar obtainIdentifierValue(String name) {
        return scopeVariables.get(name);
    }

    void registerNewVariable(String name, Scalar scalar) {
        scopeVariables.put(name, scalar);
    }

    boolean identifierIsDefined(String name) {
        return scopeVariables.containsKey(name);
    }

    void reportViolation(String msg, InputRange range){
        if(conforms){
            handler.reportViolation(msg, range);
            this.conforms = false;
        }
    }

    void reportViolation(String msg){
        if(conforms) {
            handler.reportViolation(msg);
            this.conforms = false;
        }
    }

    boolean conforms() {
        return conforms;
    }

    void print(String s) {
        emitter.print(s);
    }
}
