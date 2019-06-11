package marcosImpl;


public class NumberState extends LexerState {

    public NumberState(Context context, String accum) {
        super(context, accum);
    }

    @Override
    public State next(Character c) {
        if (String.valueOf(c).matches(LexerConstants.NUMBER)) {
            accum += c;
            return this;
        }
        addTokenToResult();
        return new InitialState(context, accum).next(c);
    }

    @Override
    TokenMarcos getToken() {
        return new TokenMarcosImpl(context.getColumnRange(), context.getRowRange(), TokenTypeMarcos.NumberLiteral, this.accum);
    }
}
