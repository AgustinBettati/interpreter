package lexer;

import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenListTest {

    private Lexer lexer = new RealLexer();

    @Test
    void simplePrintStatement() {
        validateTokens("print(variable);",
                Arrays.asList(
                        TokenType.PRINT, TokenType.LEFT_PAREN, TokenType.IDENTIFIER, TokenType.RIGHT_PAREN, TokenType.SEMI_COLON
                ));
    }

    @Test
    void simpleDeclarationStatement() {
        validateTokens("let p3pito: number;",
                Arrays.asList(
                        TokenType.LET, TokenType.SPACE, TokenType.IDENTIFIER, TokenType.COLON, TokenType.SPACE,
                        TokenType.NUMBER_TYPE, TokenType.SEMI_COLON
                ));
    }

    @Test
    void printStatementWithAdditionOfAllTypes() {
        validateTokens("print(_pi+ $jorge+\"hola\" + 5)",
                Arrays.asList(
                        TokenType.PRINT, TokenType.LEFT_PAREN, TokenType.IDENTIFIER, TokenType.ADDITION, TokenType.SPACE,
                        TokenType.IDENTIFIER,TokenType.ADDITION,TokenType.STRING_LITERAL,TokenType.SPACE,TokenType.ADDITION,
                        TokenType.SPACE, TokenType.NUMBER_LITERAL, TokenType.RIGHT_PAREN
                ));
    }

    @Test
    void expressionWithStringAndNumber() {
        validateTokens("\"hola\" + 54",
                Arrays.asList(
                        TokenType.STRING_LITERAL, TokenType.SPACE, TokenType.ADDITION, TokenType.SPACE, TokenType.NUMBER_LITERAL
                ));
    }

    @Test
    void stringWithEscapeChars() {
        validateTokens("\" ho\\\" estoy adentro de quotes\\\"la \"",
                Arrays.asList(
                        TokenType.STRING_LITERAL
                ));
    }

    @Test
    void wordWithQuotationInTheMiddel() {
        validateTokens("ho\"\"lal",
                Arrays.asList(
                        TokenType.UNKOWN
                ));
    }

    @Test
    void unkownTokens() {
        validateTokens("let 3no: number = $#lala",
                Arrays.asList(
                        TokenType.LET, TokenType.SPACE, TokenType.UNKOWN, TokenType.COLON, TokenType.SPACE, TokenType.NUMBER_TYPE,
                        TokenType.SPACE, TokenType.ASSIGN, TokenType.SPACE, TokenType.UNKOWN
                ));
    }

    @Test
    void assignationStatementWithArithmeticOperations() {
        validateTokens("x = 5 * 3+ 2/5 -1;",
                Arrays.asList(
                TokenType.IDENTIFIER, TokenType.SPACE, TokenType.ASSIGN, TokenType.SPACE, TokenType.NUMBER_LITERAL, TokenType.SPACE,
                TokenType.MULTIPLICATION, TokenType.SPACE, TokenType.NUMBER_LITERAL, TokenType.ADDITION, TokenType.SPACE, TokenType.NUMBER_LITERAL,
                TokenType.DIVISION, TokenType.NUMBER_LITERAL, TokenType.SPACE, TokenType.SUBSTRACTION, TokenType.NUMBER_LITERAL, TokenType.SEMI_COLON
                ));
    }

    @Test
    void assignationStatementOfDecimalNumber() {
        validateTokens("pi = 3.14;",
                Arrays.asList(
                TokenType.IDENTIFIER, TokenType.SPACE, TokenType.ASSIGN, TokenType.SPACE, TokenType.NUMBER_LITERAL, TokenType.SEMI_COLON
                ));
    }

    @Test
    void semiColonAfter() {
        validateTokens("let name=\"agustin\";print",
                Arrays.asList(
                TokenType.LET, TokenType.SPACE, TokenType.IDENTIFIER, TokenType.ASSIGN, TokenType.STRING_LITERAL, TokenType.SEMI_COLON, TokenType.PRINT
                ));
    }

    @Test
    void printStatementWithStringStartingWithSpace() {
        validateTokens("print(\" mundo\");",
                Arrays.asList(
                TokenType.PRINT, TokenType.LEFT_PAREN, TokenType.STRING_LITERAL, TokenType.RIGHT_PAREN, TokenType.SEMI_COLON
                ));
    }

    @Test
    void stringLiteralWithSingleQuotation() {
        validateTokens("let singleQuote: string = 'mundo';",
                Arrays.asList(
                TokenType.LET, TokenType.SPACE, TokenType.IDENTIFIER, TokenType.COLON, TokenType.SPACE, TokenType.STRING_TYPE, TokenType.SPACE,
                        TokenType.ASSIGN, TokenType.SPACE, TokenType.STRING_LITERAL, TokenType.SEMI_COLON
                ));
    }

    private void validateTokens(String src, List<TokenType> expectedTypes){
        final List<Token> tokens = lexer.generateTokens(src);
        final List<TokenType> tokenTypes = tokens.stream().map(Token::getType).collect(Collectors.toList());
        assertEquals(expectedTypes, tokenTypes);

    }
}
