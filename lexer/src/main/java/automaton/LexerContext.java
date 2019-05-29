package automaton;

import lexer.Lexer;
import token.InputRange;
import token.RealInputRange;

public class LexerContext {

    private String accum;
    private RealInputRange range;

    public LexerContext() {
        this.accum = "";
        this.range = new RealInputRange();
    }

    public LexerContext(String accum, RealInputRange range) {
        this.accum = accum;
        this.range = range;
    }

    public LexerContext resetAccum(){
        return new LexerContext("", range.startFromNextPosition());
    }

    public LexerContext newChar(Character character) {
        RealInputRange newRange;
        if(character == '\n'){
            newRange = range.moveLine();
        }
        else if (character == '\t') {
            newRange = range.moveColumn(4);
        }
        else {
            newRange = range.moveColumn(1);
        }
        return new LexerContext(accum + character, newRange);
    }

    public String getAccum() {
        return accum;
    }

    public InputRange getRange() {
        return range;
    }
}
