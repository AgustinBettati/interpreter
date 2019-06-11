package parser.statement;

import ast.EmptyNode;
import ast.Type;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import token.InputRange;
import token.RealInputRange;
import token.Token;
import token.TokenType;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractStatementParser implements StatementParser {

    ErrorHandler errorHandler;

    public AbstractStatementParser(ErrorHandler handler) {
        this.errorHandler = handler;
    }

    void setHander(ErrorHandler newHandler){
        this.errorHandler = newHandler;
    }

    boolean validateTypes(List<Token> tokens, List<TokenType> expectedTypes) {
        final List<TokenType> actualTypes = tokens.stream().map(Token::getType).collect(Collectors.toList());
        for (int i = 0; i < actualTypes.size(); i++) {
            if (actualTypes.get(i) != expectedTypes.get(i)) {
                return false;
            }
        }
        return true;
    }

    void handleInvalidStatement(List<Token> statement) {
        errorHandler.reportViolation("[PARSER] Invalid statement", getRange(statement.get(0), statement.get(statement.size()-1)));
    }

    void checkForUnkownTokens(List<Token> tokens) {
        final List<Token> invalidTokens = tokens.stream()
                .filter(token -> token.getType() == TokenType.UNKOWN)
                .collect(Collectors.toList());
        if(!invalidTokens.isEmpty()){
            invalidTokens.forEach(token -> errorHandler.reportViolation("[LEXER] Invalid token.", token.getRange()));
        }
    }

    Type fromTokenTypeToLiteralType(TokenType type) {
        if(type == TokenType.STRING_TYPE) return Type.STRING;
        else return Type.NUMBER;
    }

    InputRange getRange(Token start, Token end) {
        final InputRange startRange = start.getRange();
        final InputRange endRange = end.getRange();
        return new RealInputRange(startRange.getStartLine(), startRange.getStartColumn(), endRange.getEndLine(), endRange.getEndColumn());
    }
}
