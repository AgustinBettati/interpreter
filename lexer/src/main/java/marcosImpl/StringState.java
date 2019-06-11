package marcosImpl;


public class StringState extends LexerState {
    public StringState(Context context, String accum) {
        super(context, accum);
    }

    @Override
    TokenMarcos getToken() {

        return new TokenMarcosImpl(context.getColumnRange(), context.getRowRange(), TokenTypeMarcos.StringLiteral, accum);
    }

    @Override
    public State next(Character c) {

        if (accum.matches("\"([^\\\\\"]|\\\\\")*\"")) {
            if (LexerConstants.END_OF_TOKEN_CHARS.contains(c)) {
                addTokenToResult();
                accum = String.valueOf(c);
                return new SingleCharState(context, accum);
            }else{
                return new UnknownState(context, accum+=c);
            }
        }
        accum += c;
        return this;
    }
}
