package marcosImpl;


public class TokenMarcosImpl implements TokenMarcos {


    private Range columnRange;
    private Range rowRange;
    private TokenTypeMarcos tokenType;
    private String value;

    public TokenMarcosImpl(Range columnRange, Range rowRange, TokenTypeMarcos tokenType, String value) {
        this.columnRange = columnRange;
        this.rowRange = rowRange;
        this.tokenType = tokenType;
        this.value = value;
    }

    @Override
    public Range getColumnRange() {
        return this.columnRange;
    }

    @Override
    public Range getRowRange() {
        return this.rowRange;
    }

    @Override
    public TokenTypeMarcos getTokenType() {
        return this.tokenType;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object other){
        TokenMarcosImpl other1 = (TokenMarcosImpl) other;
        return other1.columnRange.equals(columnRange) && other1.rowRange.equals(rowRange) && other1.tokenType.equals(tokenType) && other1.value.equals(value);
    }
}
