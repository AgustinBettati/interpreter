package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.InputRange;
import token.RealToken;
import token.Token;
import token.TokenType;

abstract class AbstractLexerState implements LexerAutomatonState {

    protected LexerContext ctx;

    public abstract TokenType obtainTokenType();

    @Override
    public Token obtainToken() {
        String value = ctx.getAccum();
        InputRange range = ctx.getRange();
        ctx = ctx.resetAccum();
        return new RealToken(value, range, obtainTokenType());
    }

    public AbstractLexerState(LexerContext ctx) {
        this.ctx = ctx;
    }
}
