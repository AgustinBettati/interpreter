package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
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
        return handleNormalCase(c);
    }

    @Override
    public TokenType obtainTokenType() {
        switch (ctx.getAccum()) {
            case "let":
                return TokenType.LET;
            case "string":
                return TokenType.STRING_TYPE;
            case "number":
                return TokenType.NUMBER_TYPE;
            case "print":
                return TokenType.PRINT;
            default:
                return TokenType.IDENTIFIER;

        }
    }
}
