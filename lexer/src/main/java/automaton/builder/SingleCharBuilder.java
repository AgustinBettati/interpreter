package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.InputRange;
import token.RealToken;
import token.Token;
import token.TokenType;

import java.util.Arrays;

public class SingleCharBuilder extends AbstractLexerState {

    public SingleCharBuilder(LexerContext ctx) {
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return false;
    }

    @Override
    public LexerAutomatonState next(Character c) {
        if(ctx.getAccum().isEmpty() && Character.isDigit(c)){

            return new AlphaNumericBuilder(ctx.newChar(c)); // TODO llevar a NumberLiteral
        }
        else if(Arrays.asList(';', ':', '\n', '\t', ' ', '=', '+', '-', '*', '/').contains(c)){
            this.ctx = ctx.newChar(c);
            return this;
        }
        else if (!Character.isAlphabetic(c) || !Character.isDigit(c)) {
            return new AlphaNumericBuilder(ctx.newChar(c)); //TODO llevar a Unkown
        }
        else {
            return new AlphaNumericBuilder(ctx.newChar(c));
        }
    }

    @Override
    public TokenType obtainTokenType() {
        return TokenType.SEMI_COLON; //TODO cheaquear todos los casos
    }
}
