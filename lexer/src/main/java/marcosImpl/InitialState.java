package marcosImpl;


public class InitialState extends LexerState {


    public InitialState(Context context, String accum) {
        super(context, accum);
    }

    @Override
    public State next(Character c) {

        accum += c;
        if (LexerConstants.END_OF_TOKEN_CHARS.contains(c)) {
            return new SingleCharState(context, accum);
        }
        else if ((String.valueOf(c).matches(LexerConstants.NUMBER))){
            return new NumberState(context,accum);
        }
        else if (c=='\"'){
            return new StringState(context,accum);
        }
        return new IdentifierOrReservedWordState(this.context, this.accum);
    }

    @Override
    TokenMarcos getToken() {
        return null;
    }

}
