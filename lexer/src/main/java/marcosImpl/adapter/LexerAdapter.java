package marcosImpl.adapter;

import lexer.Lexer;
import marcosImpl.LexerImplMarcos;
import marcosImpl.Range;
import marcosImpl.TokenMarcos;
import marcosImpl.TokenTypeMarcos;
import token.*;

import java.util.List;
import java.util.stream.Collectors;

public class LexerAdapter implements Lexer {

    private LexerImplMarcos lexerMarcos;

    public LexerAdapter(LexerImplMarcos lexerMarcos) {
        this.lexerMarcos = lexerMarcos;
    }

    @Override
    public List<Token> generateTokens(String src) {
        final List<TokenMarcos> tokenMarcos = lexerMarcos.generateTokens(src);
        return tokenMarcos.stream().map(this::adaptMarcosToken).collect(Collectors.toList());
    }


    public Token adaptMarcosToken(TokenMarcos other){
        return new RealToken(other.getValue(), adaptRange(other.getColumnRange(), other.getRowRange()), adaptType(other));
    }

    private TokenType adaptType(TokenMarcos tokenMarcos) {
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.NumberType) return TokenType.NUMBER_TYPE;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.StringType) return TokenType.STRING_TYPE;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.NumberLiteral) return TokenType.NUMBER_LITERAL;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.Colon) return TokenType.COLON;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.Enter) return TokenType.NEW_LINE;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.EQUAL) return TokenType.ASSIGN;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.Identifier) return TokenType.IDENTIFIER;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.LeftParenthesis) return TokenType.LEFT_PAREN;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.RightParenthesis) return TokenType.RIGHT_PAREN;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.Let) return TokenType.LET;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.Print) return TokenType.PRINT;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.Semicolon) return TokenType.SEMI_COLON;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.Space) return TokenType.SPACE;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.StringLiteral) return TokenType.STRING_LITERAL;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.Unknown) return TokenType.UNKOWN;
        if(tokenMarcos.getTokenType() == TokenTypeMarcos.ArithmeticOperation){
            if(tokenMarcos.getValue().equals("+")) return TokenType.ADDITION;
            if(tokenMarcos.getValue().equals("*")) return TokenType.MULTIPLICATION;
            if(tokenMarcos.getValue().equals("/")) return TokenType.DIVISION;
            if(tokenMarcos.getValue().equals("-")) return TokenType.SUBSTRACTION;
            else return TokenType.UNKOWN;
        }
        else return TokenType.UNKOWN;
    }

    private InputRange adaptRange(Range columnRange, Range rowRange) {
        return new RealInputRange(rowRange.getFirst(), columnRange.getFirst(), rowRange.getSecond(), columnRange.getSecond());
    }
}
