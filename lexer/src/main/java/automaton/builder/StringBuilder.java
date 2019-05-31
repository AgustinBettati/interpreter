package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.TokenType;

public class StringBuilder extends AbstractLexerState {

    public StringBuilder(LexerContext ctx) {
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return true;
    }

    @Override
    public LexerAutomatonState next(Character c) {
        if(ctx.getAccum().matches("\"([^\\\\\"]|\\\\\")*\"")){ // el string esta cerrado
            return new SingleCharHelperState(ctx.resetAccum().addChar(c));
        }
        else if(!ctx.getAccum().startsWith("\"")){
            return new UnknownBuilder(ctx.addChar(c));
        }
        else{
            this.ctx = ctx.addChar(c);
            return this;
        }
    }

    @Override
    public TokenType obtainTokenType() {
        return TokenType.STRING_LITERAL;
    }
}
