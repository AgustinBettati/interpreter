
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TokenContainerTest {

    @Test
    public void isGreaterTest() {
        System.out.println("Test");
        TokenContainer container = new TokenContainer(new ArrayList<Token>(
                Collections.singletonList(new Token("hola"))));
        assertTrue(!container.getTokens().isEmpty());
    }
}