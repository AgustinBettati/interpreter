import errorhandler.ErrorAccumulator;
import errorhandler.ErrorHandler;
import lexer.Lexer;
import lexer.RealLexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.RealParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ExecutionTest {

    private Lexer lexer = new RealLexer();
    private Parser parser = new RealParser(lexer);
    private Interpreter interpreter = new RealInterpreter(parser);

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

    @Test
    public void declareAssignStatementWithUnmatchingTypes() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorAccumulator errorAcum = new ErrorAccumulator();
        interpreter.execute("let pi: number = \"hola\";"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, errorAcum.getErrors().size());
        assertEquals("[INTERPRETER] Expression does not conform to declared type\n" +
                "Range -> from (line: 1, col: 1) to (line: 1, col 23)", errorAcum.getErrors().get(0));
    }

    @Test
    public void declareAssignStatementWithPrint() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorHandler errorAcum = new ErrorAccumulator();
        interpreter.execute("let name:string=\"agustin\";print(name);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("agustin", msgAccum.getMessages().get(0));
    }

    @Test
    public void declareAssignArithmeticExpressionOfNumbers() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorHandler errorAcum = new ErrorAccumulator();
        interpreter.execute("let result:number= 10*2-5;print(result);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("15.0", msgAccum.getMessages().get(0));
    }

    @Test
    public void concatenationOfStringAndNumbers() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorHandler errorAcum = new ErrorAccumulator();
        interpreter.execute("let result:string= \"el numero cinco es \" + 5;print(result);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("el numero cinco es 5.0", msgAccum.getMessages().get(0));
    }

    @Test
    public void invalidMultiplicationOfStringWithNumber() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorAccumulator errorAcum = new ErrorAccumulator();
        interpreter.execute("let result:number = \"string\" * 5;print(result);"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, errorAcum.getErrors().size());
        assertEquals("[INTERPRETER] Invalid arithmetic operation for given types\n" +
                "Range -> from (line: 1, col: 21) to (line: 1, col 32)", errorAcum.getErrors().get(0));
    }

    @Test
    public void multipleStatementsWithsArithmeticOperations() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorAccumulator errorAcum = new ErrorAccumulator();
        interpreter.execute("let hola: string = \"hola\";\n" +
                        "let cuenta: number;\n" +
                        "cuenta = 5 * 5 -8 / 4;\n" +
                        "print(cuenta + hola + \" mundo\");"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, msgAccum.getMessages().size());
        assertEquals("23.0hola mundo", msgAccum.getMessages().get(0));
    }

    @Test
    public void singleStatementWithNoSemiColon() {
        MessageAccumulator msgAccum = new MessageAccumulator();
        ErrorAccumulator errorAcum = new ErrorAccumulator();
        interpreter.execute("print(5)"
                ,msgAccum
                ,errorAcum);
        assertEquals(1, errorAcum.getErrors().size());
        assertEquals("[PARSER] Missing semi-colon to end statement\n" +
                "Range -> from (line: 1, col: 8) to (line: 1, col 8)", errorAcum.getErrors().get(0));
    }


}
