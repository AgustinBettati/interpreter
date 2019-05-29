package automaton;

import token.Token;

public interface LexerAutomatonState {
    boolean isAcceptanceState();
    LexerAutomatonState next(Character c);
    Token obtainToken();
}
