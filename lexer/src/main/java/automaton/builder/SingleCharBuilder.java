package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
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
        //si tengo un char, me mando al helper on el ctx vacio y nuevo char
        //si esta vacio me quedo en el estado guardando el char
        if(specialChars.contains(c)){
            return new SingleCharBuilder(ctx.resetAccum().addChar(c));
        }
        else {
            return new SingleCharHelperState(ctx.resetAccum().addChar(c));
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
