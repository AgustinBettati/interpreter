package parser.statement;

import ast.statement.Statement;
import token.Token;

import java.util.List;

public interface StatementParser {
    Statement parseToStatement(List<Token> statementTokens);
}
