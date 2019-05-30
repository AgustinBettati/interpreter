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
        String currentAccum = ctx.getAccum() + c;
        if(Arrays.asList(';', ':', '\n', '\t', ' ', '=', '+', '-', '*', '/', '(', ')').contains(c)){
            return new SingleCharBuilder(ctx.resetAccum().addChar(c));
        }
        else if(currentAccum.matches("\\d+")){ //is a number
            return new AlphaNumericBuilder(ctx.addChar(c)); //TODO levar a NumberLiteral
        }
        else if(currentAccum.startsWith("\"")) {
            return new SingleCharBuilder(ctx.addChar(c)); //TODO llevar a StringLiteral
        }
        else if (currentAccum.matches("[A-Za-z0-9]+")) {
            return new AlphaNumericBuilder(ctx.addChar(c));
        }
        else {
            return new AlphaNumericBuilder(ctx.addChar(c)); //TODO llevar a UNKOWN
        }
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
