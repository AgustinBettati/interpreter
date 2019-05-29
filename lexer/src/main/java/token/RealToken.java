package token;

public class RealToken implements Token {

    private String value;
    private InputRange range;
    private TokenType type;

    public RealToken(String value, InputRange range, TokenType type) {
        this.value = value;
        this.range = range;
        this.type = type;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public InputRange getRange() {
        return range;
    }

    @Override
    public String getValue() {
        return value;
    }
}
