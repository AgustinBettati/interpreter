package lexer;

import automaton.builder.AlphaNumericBuilder;
import automaton.LexerAutomatonState;
import automaton.LexerContext;
import token.Token;

import java.util.LinkedList;
import java.util.List;

public class RealLexer implements Lexer {

    @Override
    public List<Token> generateTokens(String src) {

        LexerAutomatonState currentState = new AlphaNumericBuilder(new LexerContext());
        List<Token> tokens = new LinkedList<>();

        for (char character : src.toCharArray()) {
            final LexerAutomatonState newState = currentState.next(character);
            if(!newState.isAcceptanceState()){
                tokens.add(currentState.obtainToken());
            }
            currentState = newState;
        }
        tokens.add(currentState.obtainToken());

        return tokens;
    }
}
