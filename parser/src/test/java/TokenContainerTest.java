
import org.junit.jupiter.api.Test;
import token.RealInputRange;
import token.RealToken;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TokenContainerTest {

    @Test
    public void isGreaterTest() {
        Token token = new RealToken("hola",new RealInputRange(), TokenType.IDENTIFIER);
        assertSame(token.getType(), TokenType.IDENTIFIER);
    }
}