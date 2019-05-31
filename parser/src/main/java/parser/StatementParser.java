package parser;

import ast.EmptyNode;
import ast.expression.Identifier;
import ast.statement.PrintStatement;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import token.RealInputRange;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatementParser {

    private final ErrorHandler errorHandler;

    public StatementParser(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public Statement parse(List<Token> statementTokens) {
        final List<TokenType> types = statementTokens.stream().map(Token::getType).collect(Collectors.toList());
        if(types.contains(TokenType.PRINT)){
            return parsePrint(statementTokens);
        }
//        else if (types.contains(TokenType.LET) && types.contains(TokenType.ASSIGN)){
//            return parseDeclarationAsignation();
//        }
//        else if (types.contains(TokenType.LET)){
//            return parserDeclaration();
//        }
//        else{
//            return parseAssignation();
//        }
        return parsePrint(statementTokens);
    }

    private PrintStatement parsePrint(List<Token> statement) {
        if(statement.size() >= 4){
            final Token print = statement.get(0);
            final Token leftParen = statement.get(1);
            final Token rightParen = statement.get(statement.size()-1);
            if(validateTypes(Arrays.asList(print, leftParen, rightParen),
                    Arrays.asList(TokenType.PRINT, TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN))){
                return new PrintStatement(new RealInputRange(),new Identifier(new RealInputRange(),""));
            }
        }
        return null;
    }

    private boolean validateTypes(List<Token> tokens, List<TokenType> expectedTypes) {
        final List<TokenType> actualTypes = tokens.stream().map(Token::getType).collect(Collectors.toList());
        for (int i = 0; i < actualTypes.size(); i++) {
            if(actualTypes.get(i) != expectedTypes.get(i)){
                return false;
            }
        }
        return true;
    }


}
