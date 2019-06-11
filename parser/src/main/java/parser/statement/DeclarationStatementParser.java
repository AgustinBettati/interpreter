package parser.statement;

import ast.PrimitiveType;
import ast.expression.Identifier;
import ast.statement.DeclarationStatement;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;

public class DeclarationStatementParser extends AbstractStatementParser {

    public DeclarationStatementParser(ErrorHandler handler) {
        super(handler);
    }

    @Override
    public Statement parseToStatement(List<Token> statement) {
        checkForUnkownTokens(statement);
        if (statement.size() == 4) {
            final Token let = statement.get(0);
            final Token identifier = statement.get(1);
            final Token colon = statement.get(2);
            final Token type = statement.get(3);
            if (validateTypes(Arrays.asList(let, identifier, colon),
                    Arrays.asList(TokenType.LET, TokenType.IDENTIFIER, TokenType.COLON)) &&
                    (type.getType() == TokenType.STRING_TYPE || type.getType() == TokenType.NUMBER_TYPE)) {
                return new DeclarationStatement(
                        getRange(identifier, type),
                        new PrimitiveType(type.getRange(), fromTokenTypeToLiteralType(type.getType())),
                        new Identifier(identifier.getRange(), identifier.getValue()));
            }
        }
        handleInvalidStatement(statement);
        return null;
    }
}
