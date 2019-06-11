package marcosImpl;


public class UnknownState extends LexerState {
    public UnknownState(Context context, String accum) {
        super(context, accum);
    }

    @Override
    TokenMarcos getToken() {
        throw new LexerException("[LEXER ERROR] Invalid token");
    }

    @Override
    public State next(Character o) {
        throw new LexerException("[LEXER ERROR] Invalid token");
    }
}
