package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.Token;
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
        return handleNormalCase(c);
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
            default:
                if(ctx.getAccum().matches("\\d+")) return TokenType.NUMBER_LITERAL;
                else return TokenType.UNKOWN;
        }
    }
}
