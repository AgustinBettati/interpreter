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

    public AssignationStatementParser(ErrorHandler handler) {
        super(handler);
    }

    @Override
    public Statement parseToStatement(List<Token> statement) {
        if (statement.size() >= 3) {
            final Token identifier = statement.get(0);
            final Token assign = statement.get(1);
            if (validateTypes(Arrays.asList(identifier, assign),
                    Arrays.asList(TokenType.IDENTIFIER, TokenType.ASSIGN))) {
                return new AssignationStatement(
                        getRange(identifier, statement.get(statement.size() - 1)),
                        new Identifier(identifier.getRange(), identifier.getValue()),
                        new ExpressionParser(errorHandler).parse(statement.subList(2, statement.size())));
            }
        }
        handleInvalidStatement(statement);
        return null;
    }
}
