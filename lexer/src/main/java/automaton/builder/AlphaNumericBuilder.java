package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.InputRange;
import token.RealToken;
import token.Token;
import token.TokenType;

import java.util.Arrays;

public class AlphaNumericBuilder extends AbstractLexerState {

    public AlphaNumericBuilder(LexerContext ctx){
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return true;
    }

    @Override
    public LexerAutomatonState next(Character c) {
        if(ctx.getAccum().isEmpty() && Character.isDigit(c)){
            return new AlphaNumericBuilder(ctx.newChar(c)); //TODO levar a NumberLiteral
        }
        else if(Arrays.asList(';', ':', '\n', '\t').contains(c)){
            return new SingleCharBuilder(ctx.resetAccum().newChar(c));
        }
        else if (!Character.isAlphabetic(c) || !Character.isDigit(c)) {
            return new AlphaNumericBuilder(ctx.newChar(c)); //TODO llevar a Unkown
        }
        else {
            this.ctx = ctx.newChar(c);
            return this;
        }
    }

    @Override
    public TokenType obtainTokenType() {
        return TokenType.IDENTIFIER; //TODO cheaquear todos los casos
    }
}
