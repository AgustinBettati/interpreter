package lexer;

import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TokenValueAndRangeTest {

    private Lexer lexer = new RealLexer();

    @Test
    public void generateIdentifierTokenAndVerifyRange() {
        final List<Token> tokens = lexer.generateTokens("soyUnIdentifier");

        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertSame(token.getType(), TokenType.IDENTIFIER);
        assertEquals("soyUnIdentifier", token.getValue());
        assertEquals(1, (int) token.getRange().getStartColumn());
        assertEquals(15, (int) token.getRange().getEndColumn());
    }

    @Test
    public void generateIdentifierAndSemiColonTokens() {
        final List<Token> tokens = lexer.generateTokens("id;");

        assertEquals(2, tokens.size());
        final Token identifier = tokens.get(0);
        final Token semiColon = tokens.get(1);
        assertSame(TokenType.IDENTIFIER, identifier.getType());
        assertSame(TokenType.SEMI_COLON, semiColon.getType());
        assertEquals("id", identifier.getValue());
        assertEquals(";", semiColon.getValue());
        assertEquals(2, (int) identifier.getRange().getEndColumn());
        assertEquals(3, (int) semiColon.getRange().getStartColumn());
        assertEquals(3, (int) semiColon.getRange().getEndColumn());
    }

    @Test
    public void generateIdentifierSemiColonAndTypeTokens() {
        final List<Token> tokens = lexer.generateTokens("id:string");

        assertEquals(3, tokens.size());
        final Token identifier = tokens.get(0);
        final Token colon = tokens.get(1);
        final Token stringType = tokens.get(2);
        assertSame(TokenType.IDENTIFIER, identifier.getType());
        assertSame(TokenType.COLON, colon.getType());
        assertSame(TokenType.STRING_TYPE, stringType.getType());
        assertEquals("id", identifier.getValue());
        assertEquals(":", colon.getValue());
        assertEquals("string", stringType.getValue());
        assertEquals(2, (int) identifier.getRange().getEndColumn());
        assertEquals(3, (int) colon.getRange().getEndColumn());
        assertEquals(4, (int) stringType.getRange().getStartColumn());
        assertEquals(9, (int) stringType.getRange().getEndColumn());
    }

    @Test
    public void testingNewLineAndRanges() {
        final List<Token> tokens = lexer.generateTokens("print(pi)\nlet num: number;");
        final Token piIdentifier = tokens.get(2);
        final Token numIdentifier = tokens.get(7);

        assertEquals(12, tokens.size());
        assertEquals(7, (int) piIdentifier.getRange().getStartColumn());
        assertEquals(1, (int) piIdentifier.getRange().getStartLine());
        assertEquals(5, (int) numIdentifier.getRange().getStartColumn());
        assertEquals(2, (int) numIdentifier.getRange().getStartLine());
        assertEquals("num", numIdentifier.getValue());
        assertEquals("pi", piIdentifier.getValue());
    }

}
