package marcosImpl;


public class IdentifierOrReservedWordState extends LexerState {

    public IdentifierOrReservedWordState(Context context, String accum) {
        super(context, accum);
    }

    @Override
    TokenMarcos getToken() {

        TokenTypeMarcos tokenType;
        switch (accum) {
            case "print":
                tokenType = TokenTypeMarcos.Print;
                break;
            case "let":
                tokenType = TokenTypeMarcos.Let;
                break;
            case "string":
                tokenType = TokenTypeMarcos.StringType;
                break;
            case "number":
                tokenType = TokenTypeMarcos.NumberType;
                break;
            case "boolean":
                tokenType = TokenTypeMarcos.BooleanType;
                break;

            case "true":

            case "false":
                tokenType = TokenTypeMarcos.BooleanLiteral;
                break;

            default:
                tokenType = TokenTypeMarcos.Identifier;
                break;

        }

        return new TokenMarcosImpl(context.getColumnRange(), context.getRowRange(), tokenType, accum);
    }


    @Override
    public State next(Character c) {
        if (LexerConstants.END_OF_TOKEN_CHARS.contains(c)) {
            addTokenToResult();
            accum = String.valueOf(c);
            return new SingleCharState(context, accum);
        } else if (String.valueOf(c).matches(LexerConstants.LETTER)) {
            accum += c;
            return this;
        } else return new InitialState(context, accum).next(c);

    }
}
