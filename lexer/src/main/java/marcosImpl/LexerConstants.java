package marcosImpl;


import java.util.Arrays;
import java.util.List;

public class LexerConstants {

    static final List<Character> END_OF_TOKEN_CHARS = Arrays.asList(';', ':', '\n', '\t', ' ', '=', '+', '-', '*', '/', '(', ')');
    static final List<Character> OPERATOR_CHARS =  Arrays.asList('+','-','*','/');;
    static final String NUMBER =  "[0-9]";
    static final String LETTER =  "[a-zA-Z]";


}
