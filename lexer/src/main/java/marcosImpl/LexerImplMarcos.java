package marcosImpl;


import java.util.List;

public class LexerImplMarcos implements LexerMarcos {
    private Context context;
    private State currentState;


    public LexerImplMarcos(Context context, State currentState) {
        this.context = context;
        this.currentState = currentState;
    }

    public LexerImplMarcos(Context context) {
        this.context = context;
        this.currentState = new InitialState(context, "");
    }

    public LexerImplMarcos() {
        this.context = new ContextImpl(0, 0, 0, 0);
        this.currentState = new InitialState(context, "");

    }

    @Override
    public List<TokenMarcos> generateTokens(String file) {
        for (int i = 0; i < file.length(); i++) {
            char actual = file.charAt(i);
            this.currentState = currentState.next(actual);
            if (actual == '\n') {
                context.nextRow();
            } else {
                context.nextColumn();
            }
        }
        this.currentState.addTokenToResult();

        return context.getResult();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
