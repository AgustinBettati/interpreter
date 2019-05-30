package lexer;

import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenListTest {

    private Lexer lexer = new RealLexer();

    @Test
    public void simplePrintStatement() {
        validateTokens("print(variable);",
                Arrays.asList(
                        TokenType.PRINT, TokenType.LEFT_PAREN, TokenType.IDENTIFIER, TokenType.RIGHT_PAREN, TokenType.SEMI_COLON
                ));
    }

    @Test
    public void simpleDeclarationStatement() {
        validateTokens("let pepito: number;",
                Arrays.asList(
                        TokenType.LET, TokenType.SPACE, TokenType.IDENTIFIER, TokenType.COLON, TokenType.SPACE,
                        TokenType.NUMBER_TYPE, TokenType.SEMI_COLON
                ));
    }

    @Test
    public void printStatementWithAddition() {
        validateTokens("print(pi+ jorge)",
                Arrays.asList(
                        TokenType.PRINT, TokenType.LEFT_PAREN, TokenType.IDENTIFIER, TokenType.ADDITION, TokenType.SPACE,
                        TokenType.IDENTIFIER, TokenType.RIGHT_PAREN
                ));
    }


    private void validateTokens(String src, List<TokenType> expectedTypes){
        final List<Token> tokens = lexer.generateTokens(src);
        final List<TokenType> tokenTypes = tokens.stream().map(Token::getType).collect(Collectors.toList());
        assertEquals(expectedTypes, tokenTypes);

    }
}
