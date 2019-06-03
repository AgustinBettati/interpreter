import errorhandler.ErrorAccumulator;
import errorhandler.ErrorHandler;
import lexer.Lexer;
import lexer.RealLexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.RealParser;
import token.Token;
import token.TokenType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ExecutionTest {

    private Lexer lexer = new RealLexer();
    Parser parser = new RealParser(lexer);
    Interpreter interpreter = new RealInterpreter(parser);

    @Test
    public void printLiteral() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorHandler errorAcum = new ErrorAccumulator();
        interpreter.execute("print(4);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("4.0", msgAccum.getMessages().get(0));
    }

    @Test
    public void declareAssignAndPrintLiteral() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorHandler errorAcum = new ErrorAccumulator();
        interpreter.execute("let pi: number; pi = 3.14; print(pi);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("3.14", msgAccum.getMessages().get(0));
    }


}
