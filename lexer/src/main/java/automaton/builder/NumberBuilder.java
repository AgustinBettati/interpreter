package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.TokenType;

public class NumberBuilder extends AbstractLexerState {

    NumberBuilder(LexerContext ctx) {
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
        else if(Character.isDigit(c)){
            this.ctx = ctx.addChar(c);
            return this;
        }
        else if(c =='.' && !ctx.getAccum().contains(".")){
            this.ctx = ctx.addChar(c);
            return this;
        }
        else if(c.toString().matches("[A-Za-z0-9]+") && ctx.getAccum().isEmpty()){
            return new AlphaNumericBuilder(ctx.addChar(c));
        }
        else{
            return new UnknownBuilder(ctx.addChar(c));
        }
    }

    @Override
    public TokenType obtainTokenType() {
        if(ctx.getAccum().endsWith(".")){
            return TokenType.UNKOWN;
        }
        return TokenType.NUMBER_LITERAL;
    }
}
