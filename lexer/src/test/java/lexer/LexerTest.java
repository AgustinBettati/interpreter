package lexer;

import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {

    Lexer lexer = new RealLexer();

    @Test
    public void generateIdentifierToken() {
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
}
