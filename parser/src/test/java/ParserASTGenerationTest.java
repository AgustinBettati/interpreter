
import ast.ArithmeticOperation;
import ast.PrimitiveType;
import ast.Program;
import ast.Type;
import ast.expression.ArithmeticExpression;
import ast.expression.Identifier;
import ast.expression.Literal;
import ast.statement.AssignationStatement;
import ast.statement.DeclarationAssignationStatement;
import ast.statement.DeclarationStatement;
import ast.statement.PrintStatement;
import errorhandler.ErrorAccumulator;
import lexer.Lexer;
import lexer.RealLexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.RealParser;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ParserASTGenerationTest {
    private Lexer lexer = new RealLexer();
    Parser parser = new RealParser(lexer);

    @Test
    public void printStatementWithNumberLiteral() {
        final Program result = (Program) parser.parse("print(5);", new ErrorAccumulator());

        final PrintStatement print = (PrintStatement) result.getStatements().get(0);

        final Literal exp = (Literal) print.getExpressionToPrint();

        assertEquals(1,result.getStatements().size());
        assertEquals(Type.NUMBER, exp.getType());
        assertEquals(5.0, exp.getNumberValue());
        assertEquals(7, exp.getInputRange().getStartColumn());
        assertEquals(7, exp.getInputRange().getEndColumn());
    }

    @Test
    public void printStatementWithStringLiteral() {
        final Program result = (Program) parser.parse("print(\"hola\");", new ErrorAccumulator());

        final PrintStatement print = (PrintStatement) result.getStatements().get(0);

        final Literal exp = (Literal) print.getExpressionToPrint();

        assertEquals(1,result.getStatements().size());
        assertEquals(Type.STRING, exp.getType());
        assertEquals("hola", exp.getStringValue());
    }

    @Test
    public void printStatementWithMultipleArithmeticOperations() {
        final Program result = (Program) parser.parse("print(5 * 5 -8/ 2 + \"hola\");", new ErrorAccumulator());

        final PrintStatement print = (PrintStatement) result.getStatements().get(0);
        final ArithmeticExpression addition = (ArithmeticExpression) print.getExpressionToPrint();
        final Literal hola = (Literal) addition.getRight();
        final ArithmeticExpression substraction = (ArithmeticExpression) addition.getLeft();
        final ArithmeticExpression multiplication = (ArithmeticExpression) substraction.getLeft();
        final ArithmeticExpression division = (ArithmeticExpression) substraction.getRight();

        assertEquals(1,result.getStatements().size());
        assertEquals(ArithmeticOperation.ADDITION, addition.getOperation());
        assertEquals(ArithmeticOperation.SUBSTRACTION, substraction.getOperation());
        assertEquals(ArithmeticOperation.MULTIPLICATION, multiplication.getOperation());
        assertEquals(ArithmeticOperation.DIVISION, division.getOperation());
        assertEquals(Type.STRING, hola.getType());
        assertEquals("hola", hola.getStringValue());
    }

    @Test
    public void invalidExpressionInsidePrintStatement() {
        final ErrorAccumulator handler = new ErrorAccumulator();
        final Program result = (Program) parser.parse("print(5*);", handler);

        assertEquals("[PARSER] Invalid expression\n" +
                "Range -> from (line: 1, col: 7) to (line: 1, col 8)", handler.getErrors().get(0));
    }

    @Test
    public void reservedWordInsidePrintStatement() {
        final ErrorAccumulator handler = new ErrorAccumulator();
        final Program result = (Program) parser.parse("print(let);", handler);

        assertEquals("[PARSER] Expected expression but found let\n" +
                "Range -> from (line: 1, col: 7) to (line: 1, col 9)", handler.getErrors().get(0));
    }

    @Test
    public void assignationStatementOfNumberLiteral() {
        final Program result = (Program) parser.parse("x = 1;", new ErrorAccumulator());

        final AssignationStatement assignation = (AssignationStatement) result.getStatements().get(0);
        final Identifier identifier = assignation.getIdentifier();
        final Literal number = (Literal) assignation.getExpression();


        assertEquals(1,result.getStatements().size());
        assertEquals("x", identifier.getName());
        assertEquals(Type.NUMBER, number.getType());
        assertEquals(1, number.getNumberValue());
    }

    @Test
    public void declarationStatement() {
        final Program result = (Program) parser.parse("let pi: number;", new ErrorAccumulator());
        final DeclarationStatement declaration = (DeclarationStatement) result.getStatements().get(0);
        final PrimitiveType primitiveType = declaration.getType();
        final Identifier identifier = declaration.getIdentifier();

        assertEquals(1,result.getStatements().size());
        assertEquals(Type.NUMBER, primitiveType.getType());
        assertEquals("pi", identifier.getName());
    }

    @Test
    public void declarationAndAssignationStatementsInTwoLines() {
        final Program result = (Program) parser.parse("let pi: number;\npi = 3.14;", new ErrorAccumulator());
        final DeclarationStatement declaration = (DeclarationStatement) result.getStatements().get(0);
        final AssignationStatement assignation = (AssignationStatement) result.getStatements().get(1);
        final PrimitiveType primitiveType = declaration.getType();
        final Identifier identifier = declaration.getIdentifier();
        final Literal number = (Literal) assignation.getExpression();

        assertEquals(2,result.getStatements().size());
        assertEquals(Type.NUMBER, primitiveType.getType());
        assertEquals("pi", identifier.getName());
        assertEquals(Type.NUMBER, number.getType());
        assertEquals(3.14, number.getNumberValue());
    }

    @Test
    public void declarationAssignationStatementOfString() {
        final Program result = (Program) parser.parse("let name : number = \"agustin\";", new ErrorAccumulator());
        final DeclarationAssignationStatement declareAssign = (DeclarationAssignationStatement) result.getStatements().get(0);
        final PrimitiveType primitiveType = declareAssign.getType();
        final Identifier identifier = declareAssign.getIdentifier();
        final Literal number = (Literal) declareAssign.getExpression();

        assertEquals(1,result.getStatements().size());
        assertEquals(Type.NUMBER, primitiveType.getType());
        assertEquals("name", identifier.getName());
        assertEquals(Type.STRING, number.getType());
        assertEquals("agustin", number.getStringValue());
    }


}