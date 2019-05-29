import token.Token;

import java.util.List;

public class TokenContainer {
    private List<Token> tokens;

    public TokenContainer(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
