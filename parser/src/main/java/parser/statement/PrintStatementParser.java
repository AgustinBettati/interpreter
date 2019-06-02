package parser.statement;

import ast.statement.PrintStatement;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import parser.ExpressionParser;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;

public class PrintStatementParser extends AbstractStatementParser {

    public PrintStatementParser(ErrorHandler handler) {
        super(handler);
    }

    @Override
    public Statement parseToStatement(List<Token> statement) {
        if (statement.size() >= 4) {
            final Token print = statement.get(0);
            final Token leftParen = statement.get(1);
            final Token rightParen = statement.get(statement.size() - 1);
            if (validateTypes(Arrays.asList(print, leftParen, rightParen),
                    Arrays.asList(TokenType.PRINT, TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN))) {
                return new PrintStatement(
                        getRange(print, rightParen),
                        new ExpressionParser(errorHandler).parse(statement.subList(2, statement.size() - 1)));
            }
        }
        handleInvalidStatement(statement);
        return null;
    }
}
