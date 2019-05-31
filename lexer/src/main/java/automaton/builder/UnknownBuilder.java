package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.TokenType;

public class UnknownBuilder extends AbstractLexerState {

    public UnknownBuilder(LexerContext ctx) {
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return true;
    }

    @Override
    public LexerAutomatonState next(Character c) {
        if(specialChars.contains(c)){
            return new SingleCharBuilder(ctx.resetAccum().addChar(c));
        }
        else{
            this.ctx = ctx.addChar(c);
            return this;
        }
    }

    @Override
    public TokenType obtainTokenType() {
        return TokenType.UNKOWN;
    }

}
