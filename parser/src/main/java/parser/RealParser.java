package parser;

import ast.ASTNode;
import ast.EmptyNode;
import ast.Program;
import ast.statement.Statement;
import errorhandler.ErrorHandler;
import lexer.Lexer;
import parser.statement.StatementParser;
import parser.statement.StatementParserProvider;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RealParser implements Parser {

    private Lexer lexer;
    private StatementParserProvider provider;

    public RealParser(Lexer lexer) {
        this.lexer = lexer;
        provider = new StatementParserProvider();
    }

    @Override
    public ASTNode parse(String src, ErrorHandler handler) {
        List<Token> tokens = lexer.generateTokens(src);

        final List<Token> invalidTokens = tokens.stream()
                .filter(token -> token.getType() == TokenType.UNKOWN)
                .collect(Collectors.toList());
        if(!invalidTokens.isEmpty()){
            invalidTokens.forEach(token -> handler.reportViolation("[LEXER] Invalid token.", token.getRange()));
            return new EmptyNode();
        }

        final List<Token> filtered = tokens.stream().filter(token -> token.getType() != TokenType.SPACE && token.getType() != TokenType.TAB
                && token.getType() != TokenType.NEW_LINE).collect(Collectors.toList());

        provider.setHandler(handler);
        List<Token> statementTokens = new ArrayList<>();
        List<Statement> generatedStatements = new ArrayList<>();
        for (Token token : filtered) {
            if(token.getType() == TokenType.SEMI_COLON){
                generatedStatements.add(provider.obtainParser(statementTokens).parseToStatement(statementTokens));
                statementTokens = new ArrayList<>();
            }
            else {
                statementTokens.add(token);
            }
        }
        if(!statementTokens.isEmpty()) generatedStatements.add(provider.obtainParser(statementTokens).parseToStatement(statementTokens));
        return new Program(generatedStatements);
    }
}
