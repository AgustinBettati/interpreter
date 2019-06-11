package marcosImpl;

public abstract class LexerState implements State {

    Context context;
    String accum;

    public LexerState(Context context, String accum) {
        this.context = context;
        this.accum = accum;
    }

    public void addTokenToResult() {
        context.addToken(this.getToken());
        context.restart();
        accum = "";
    }

    abstract TokenMarcos getToken();


}
