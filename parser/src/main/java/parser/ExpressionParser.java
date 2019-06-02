package parser;

import ast.ArithmeticOperation;
import ast.EmptyNode;
import ast.Type;
import ast.expression.ArithmeticExpression;
import ast.expression.Expression;
import ast.expression.Identifier;
import ast.expression.Literal;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import token.InputRange;
import token.RealInputRange;
import token.Token;
import token.TokenType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExpressionParser {

    private final ErrorHandler errorHandler;

    public ExpressionParser(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public Expression parse(List<Token> tokens) {
        return tryParsingArithmeticOperationOf(TokenType.ADDITION, tokens).orElse(
            tryParsingArithmeticOperationOf(TokenType.SUBSTRACTION, tokens).orElse(
                tryParsingArithmeticOperationOf(TokenType.MULTIPLICATION, tokens).orElse(
                    tryParsingArithmeticOperationOf(TokenType.DIVISION, tokens).orElse(
                        parseIndentifierOrLiteral(tokens)
                    )
                )
            )
        );
    }

    private Optional<Expression> tryParsingArithmeticOperationOf(TokenType type,List<Token> tokens) {
        final int size = tokens.size();
        if(size < 3) return Optional.empty();
        final Token start = tokens.get(0);
        final Token end = tokens.get(size - 1);
        final List<TokenType> types = tokens.stream().map(Token::getType).collect(Collectors.toList());
        final int operationIndex = types.indexOf(type);
        if (operationIndex != -1) {
            final List<Token> left = tokens.subList(0, operationIndex);
            final List<Token> right = tokens.subList(operationIndex+1, size);
            return Optional.of(new ArithmeticExpression(getRange(start, end),
                    parse(left),
                    parse(right),
                    fromTokenTypeToArithmeticOperation(type)));
        } else return Optional.empty();
    }

    private ArithmeticOperation fromTokenTypeToArithmeticOperation(TokenType type) {
        if(type == TokenType.ADDITION) return ArithmeticOperation.ADDITION;
        else if(type == TokenType.SUBSTRACTION) return ArithmeticOperation.SUBSTRACTION;
        else if(type == TokenType.MULTIPLICATION) return ArithmeticOperation.MULTIPLICATION;
        else return ArithmeticOperation.DIVISION;
    }

    private Expression parseIndentifierOrLiteral(List<Token> tokens){
        if(tokens.isEmpty()){
            errorHandler.reportViolation("[PARSER] Expected expression not defined");
            return null;
        }
        else if(tokens.size()>1){
            errorHandler.reportViolation("[PARSER] Invalid expression", getRange(tokens.get(0), tokens.get(tokens.size()-1)));
        }
        final Token token = tokens.get(0);
        if(token.getType() == TokenType.IDENTIFIER) return new Identifier(token.getRange(), token.getValue());
        else if (token.getType() == TokenType.NUMBER_LITERAL) return new Literal(token.getRange(), Type.NUMBER, token.getValue());
        else if (token.getType() == TokenType.STRING_LITERAL) return new Literal(token.getRange(), Type.STRING, token.getValue());
        else {
            errorHandler.reportViolation("[PARSER] Expected expression but found " + token.getValue(),token.getRange());
            return null;
        }
    }


    private InputRange getRange(Token start, Token end) {
        final InputRange startRange = start.getRange();
        final InputRange endRange = end.getRange();
        return new RealInputRange(startRange.getStartLine(), startRange.getStartColumn(), endRange.getEndLine(), endRange.getEndColumn());
    }

}
