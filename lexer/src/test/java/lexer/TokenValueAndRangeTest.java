package lexer;

import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenValueAndRangeTest {

    private Lexer lexer = new RealLexer();

    @Test
    public void generateIdentifierToken() {
        final List<Token> tokens = lexer.generateTokens("soyUnIdentifier");

        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertSame(TokenType.IDENTIFIER, token.getType());
        assertEquals("soyUnIdentifier", token.getValue());
        assertEquals(1, (int) token.getRange().getStartColumn());
        assertEquals(15, (int) token.getRange().getEndColumn());
    }

    @Test
    public void identifierAndSemiColon() {
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
    public void identifierSemiColonAndType() {
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
    public void newLineWithStatements() {
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

    @Test
    public void emptyStringAndNumber() {
        final List<Token> tokens = lexer.generateTokens("\"\" + 430");
        final Token emptyString = tokens.get(0);
        final Token number = tokens.get(4);
        final Token leftSpace = tokens.get(1);

        assertEquals(5, tokens.size());
        assertEquals(1, (int) emptyString.getRange().getStartColumn());
        assertEquals(2, (int) emptyString.getRange().getEndColumn());
        assertEquals(3, (int) leftSpace.getRange().getStartColumn());
        assertEquals(3, (int) leftSpace.getRange().getEndColumn());
        assertEquals(8, (int) number.getRange().getEndColumn());
        assertEquals("\"\"", emptyString.getValue());
        assertEquals("430", number.getValue());
        assertEquals(" ", leftSpace.getValue());
    }

    @Test
    public void decimalNumberToken() {
        final List<Token> tokens = lexer.generateTokens("3.14");

        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertSame(TokenType.NUMBER_LITERAL, token.getType());
        assertEquals("3.14", token.getValue());
        assertEquals(1, (int) token.getRange().getStartColumn());
        assertEquals(4, (int) token.getRange().getEndColumn());
    }

    @Test
    public void invalidDecimalNumber() {
        final List<Token> tokens = lexer.generateTokens("30.14.;");

        assertEquals(2, tokens.size());
        final Token unkown = tokens.get(0);
        final Token semiColon = tokens.get(1);
        assertSame(TokenType.UNKOWN, unkown.getType());
        assertSame(TokenType.SEMI_COLON, semiColon.getType());
    }

}
