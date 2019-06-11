package parser.statement;

import ast.expression.Identifier;
import ast.statement.AssignationStatement;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import parser.ExpressionParser;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;

public class AssignationStatementParser extends AbstractStatementParser {

    private ExpressionParser parser;

    AssignationStatementParser(ErrorHandler handler, ExpressionParser parser) {
        super(handler);
        this.parser = parser;
    }

    @Override
    public Statement parseToStatement(List<Token> statement) {
        checkForUnkownTokens(statement);
        if (statement.size() >= 3) {
            final Token identifier = statement.get(0);
            final Token assign = statement.get(1);
            if (validateTypes(Arrays.asList(identifier, assign),
                    Arrays.asList(TokenType.IDENTIFIER, TokenType.ASSIGN))) {
                return new AssignationStatement(
                        getRange(identifier, statement.get(statement.size() - 1)),
                        new Identifier(identifier.getRange(), identifier.getValue()),
                        parser.parse(statement.subList(2, statement.size())));
            }
        }
        handleInvalidStatement(getRange(statement.get(0), statement.get(statement.size() -1)));
        return null;
    }
}
