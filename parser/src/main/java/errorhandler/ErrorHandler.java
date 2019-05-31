package errorhandler;

import token.InputRange;

public interface ErrorHandler {
    void reportViolation(String message, InputRange range);
}
