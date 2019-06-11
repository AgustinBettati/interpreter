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

    private ExpressionParser expressionParser;

    PrintStatementParser(ErrorHandler handler, ExpressionParser parser) {
        super(handler);
        this.expressionParser = parser;
    }

    @Override
    public Statement parseToStatement(List<Token> statement) {
        checkForUnkownTokens(statement);
        if (statement.size() >= 4) {
            final Token print = statement.get(0);
            final Token leftParen = statement.get(1);
            final Token rightParen = statement.get(statement.size() - 1);
            if (validateTypes(Arrays.asList(print, leftParen, rightParen),
                    Arrays.asList(TokenType.PRINT, TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN))) {
                return new PrintStatement(
                        getRange(print, rightParen),
                        expressionParser.parse(statement.subList(2, statement.size() - 1)));
            }
        }
        handleInvalidStatement(getRange(statement.get(0), statement.get(statement.size() -1)));
        return null;
    }
}
