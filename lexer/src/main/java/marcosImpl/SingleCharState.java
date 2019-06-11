package marcosImpl;


public class SingleCharState extends LexerState {
    public SingleCharState(Context context, String accum) {
        super(context, accum);
    }

    @Override
    public State next(Character o) {
        addTokenToResult();
        return new InitialState(context, accum).next(o);
    }

    @Override
    TokenMarcos getToken() {
        TokenTypeMarcos tokenType;
        switch (accum) {
            case ";":
                tokenType = TokenTypeMarcos.Semicolon;
                break;
            case ":":
                tokenType = TokenTypeMarcos.Colon;
                break;
            case "\n":
                tokenType = TokenTypeMarcos.Enter;
                break;
            case " ":
                tokenType = TokenTypeMarcos.Space;
                break;
            case ",":
                tokenType = TokenTypeMarcos.Comma;
                break;
            case "(":
                tokenType = TokenTypeMarcos.LeftParenthesis;
                break;
            case ")":
                tokenType = TokenTypeMarcos.RightParenthesis;
                break;
            case "=":
                tokenType = TokenTypeMarcos.EQUAL;
                break;

            default:
                if (LexerConstants.OPERATOR_CHARS.contains(accum.charAt(0))) {
                    tokenType = TokenTypeMarcos.ArithmeticOperation;
                } else tokenType = TokenTypeMarcos.Unknown;
                break;
        }

        return new TokenMarcosImpl(context.getColumnRange(), context.getRowRange(), tokenType, this.accum);
    }
}
