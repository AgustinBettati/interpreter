import errorhandler.ErrorAccumulator;
import lexer.Lexer;
import lexer.RealLexer;
import marcosImpl.LexerImplMarcos;
import marcosImpl.adapter.LexerAdapter;
import parser.Parser;
import parser.RealParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
//        Lexer lexer = new RealLexer();
        Lexer lexer = new LexerAdapter(new LexerImplMarcos());
        Parser parser = new RealParser(lexer);

        ErrorAccumulator errorAcum = new ErrorAccumulator();
        MessageAccumulator printAccum = new MessageAccumulator();

        Interpreter interpreter = new RealInterpreter(parser);

        String src = new Scanner(new File("/Users/abettati/projects/interpreter-implementation/runner/src/main/java/program.txt"))
                .useDelimiter("\\A").next();

        interpreter.execute(src, printAccum, errorAcum);

        if(errorAcum.getErrors().isEmpty()){
            System.out.println("SUCCESSFUL EXECUTION\n");
            printAccum.getMessages().forEach(msg-> System.out.println("> " + msg));
        }
        else {
            System.out.println("FAILED EXECUTION\n");
            errorAcum.getErrors().forEach(msg -> System.out.println(msg + "\n"));
        }
    }

}
