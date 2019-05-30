package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.TokenType;

import java.util.Arrays;

public class SingleCharHelperState extends AbstractLexerState {

    public SingleCharHelperState(LexerContext ctx) {
        super(ctx);
    }

    @Override
    public boolean isAcceptanceState() {
        return false;
    }

    @Override
    public LexerAutomatonState next(Character c) {
        //en este estado se que no me llega un single char.
        String currentAccum = ctx.getAccum() + c;
        if(currentAccum.matches("\\d+")){ //is a number
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
        switch(ctx.getAccum()){
            case ":": return TokenType.COLON;
            case ";": return TokenType.SEMI_COLON;
            case " ": return TokenType.SPACE;
            case "\t": return TokenType.TAB;
            case "\n": return TokenType.NEW_LINE;
            case "+": return TokenType.ADDITION;
            case "-": return TokenType.SUBSTRACTION;
            case "*": return TokenType.MULTIPLICATION;
            case "/": return TokenType.DIVISION;
            case "=": return TokenType.ASSIGN;
            case "(": return TokenType.LEFT_PAREN;
            case ")": return TokenType.RIGHT_PAREN;
            default: return TokenType.UNKOWN;
        }
    }
}
