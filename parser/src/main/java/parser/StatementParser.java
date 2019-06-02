package parser;

import ast.ArithmeticOperation;
import ast.EmptyNode;
import ast.PrimitiveType;
import ast.Type;
import ast.expression.Identifier;
import ast.statement.AssignationStatement;
import ast.statement.DeclarationStatement;
import ast.statement.PrintStatement;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import token.InputRange;
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
        if (types.contains(TokenType.PRINT)) {
            return parsePrint(statementTokens);
        }
//        else if (types.contains(TokenType.LET) && types.contains(TokenType.ASSIGN)){
//            return parseDeclarationAsignation();
//        }
        else if (types.contains(TokenType.LET)){
            return parseDeclaration(statementTokens);
        }
        else {
            return parseAssignation(statementTokens);
        }
    }

    private DeclarationStatement parseDeclaration(List<Token> statement) {
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

    private AssignationStatement parseAssignation(List<Token> statement) {
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

    private PrintStatement parsePrint(List<Token> statement) {
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

    private boolean validateTypes(List<Token> tokens, List<TokenType> expectedTypes) {
        final List<TokenType> actualTypes = tokens.stream().map(Token::getType).collect(Collectors.toList());
        for (int i = 0; i < actualTypes.size(); i++) {
            if (actualTypes.get(i) != expectedTypes.get(i)) {
                return false;
            }
        }
        return true;
    }

    private void handleInvalidStatement(List<Token> statement) {
        errorHandler.reportViolation("[PARSER] Invalid statement", getRange(statement.get(0), statement.get(statement.size()-1)));
    }

    private Type fromTokenTypeToLiteralType(TokenType type) {
        if(type == TokenType.STRING_TYPE) return Type.STRING;
        else return Type.NUMBER;
    }

    private InputRange getRange(Token start, Token end) {
        final InputRange startRange = start.getRange();
        final InputRange endRange = end.getRange();
        return new RealInputRange(startRange.getStartLine(), startRange.getStartColumn(), endRange.getEndLine(), endRange.getEndColumn());
    }


}
