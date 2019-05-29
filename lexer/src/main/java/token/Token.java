package token;

public interface Token {
    TokenType getType();
    InputRange getRange();
    String getValue();
}
