package errorhandler;

import token.InputRange;

import java.util.LinkedList;
import java.util.List;

public class ErrorAccumulator implements ErrorHandler {

    private List<String> errors = new LinkedList<>();


    @Override
    public void reportViolation(String message, InputRange range) {
        errors.add(message + "\nRange -> from (line: "+range.getStartLine()+", col: "+range.getStartColumn()+
                ") to (line: "+range.getEndLine()+", col "+range.getEndColumn()+")");
    }

    @Override
    public void reportViolation(String message) {
        errors.add(message);
    }

    public List<String> getErrors() { return errors;}
}
