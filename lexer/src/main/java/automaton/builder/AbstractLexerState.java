package automaton.builder;

import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.InputRange;
import token.RealToken;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;

abstract class AbstractLexerState implements LexerAutomatonState {

    LexerContext ctx;
    List<Character> specialChars = Arrays.asList(';', ':', '\n', '\t', ' ', '=', '+', '-', '*', '/', '(', ')');

    public abstract TokenType obtainTokenType();

    @Override
    public Token obtainToken() {
        String value = ctx.getAccum();
        InputRange range = ctx.getRange();
        return new RealToken(value, range, obtainTokenType());
    }

    AbstractLexerState handleNormalCase(Character c){
        String currentAccum = ctx.getAccum() + c;
        if(specialChars.contains(c)){
            return new SingleCharBuilder(ctx.resetAccum().addChar(c));
        }
        else if(currentAccum.matches("[0-9]+(\\.[0-9]*)?")){ //is a number
            return new NumberBuilder(ctx.addChar(c));
        }
        else if(currentAccum.startsWith("\"")) {
            return new StringBuilder(ctx.addChar(c));
        }
        else if (currentAccum.matches("[A-Za-z_$][A-Za-z0-9_$]*")) {
            return new AlphaNumericBuilder(ctx.addChar(c));
        }
        else {
            return new UnknownBuilder(ctx.addChar(c));
        }
    }

    AbstractLexerState(LexerContext ctx) {
        this.ctx = ctx;
    }
}
