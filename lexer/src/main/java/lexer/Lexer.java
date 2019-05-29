package lexer;

import token.Token;

import java.util.List;

public interface Lexer {
    List<Token> generateTokens(String src);
}
