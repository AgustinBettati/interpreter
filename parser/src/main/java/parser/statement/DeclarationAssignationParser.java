package parser.statement;

import ast.PrimitiveType;
import ast.expression.Identifier;
import ast.statement.DeclarationAssignationStatement;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import parser.ExpressionParser;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;

public class DeclarationAssignationParser extends AbstractStatementParser {

    public DeclarationAssignationParser(ErrorHandler handler) {
        super(handler);
    }

    @Override
    public Statement parseToStatement(List<Token> statement) {
        if (statement.size() >= 6) {
            final Token let = statement.get(0);
            final Token identifier = statement.get(1);
            final Token colon = statement.get(2);
            final Token type = statement.get(3);
            final Token assign = statement.get(4);
            if (validateTypes(Arrays.asList(let, identifier, colon,assign),
                    Arrays.asList(TokenType.LET, TokenType.IDENTIFIER, TokenType.COLON, TokenType.ASSIGN)) &&
                    (type.getType() == TokenType.STRING_TYPE || type.getType() == TokenType.NUMBER_TYPE)) {
                return new DeclarationAssignationStatement(
                        getRange(let, statement.get(statement.size()-1)),
                        new PrimitiveType(type.getRange(), fromTokenTypeToLiteralType(type.getType())),
                        new Identifier(identifier.getRange(), identifier.getValue()),
                        new ExpressionParser(errorHandler).parse(statement.subList(5, statement.size()))
                );
            }
        }
        handleInvalidStatement(statement);
        return null;
    }
}
