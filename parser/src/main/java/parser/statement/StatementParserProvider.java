package parser.statement;

import errorhandler.ErrorAccumulator;
import errorhandler.ErrorHandler;
import token.Token;
import token.TokenType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatementParserProvider {

    private AssignationStatementParser assignParser;
    private DeclarationAssignationParser declareAssignParser;
    private PrintStatementParser printParser;
    private DeclarationStatementParser declareParser;

    public StatementParserProvider() {
        ErrorHandler defaultHandler = new ErrorAccumulator();
        assignParser = new AssignationStatementParser(defaultHandler);
        declareAssignParser = new DeclarationAssignationParser(defaultHandler);
        printParser = new PrintStatementParser(defaultHandler);
        declareParser = new DeclarationStatementParser(defaultHandler);
    }

    public void setHandler(ErrorHandler handler) {
        final List<AbstractStatementParser> parsers = Arrays.asList(assignParser, declareAssignParser, printParser, declareParser);
        parsers.forEach(parser -> parser.setHander(handler));
    }

    public StatementParser obtainParser(List<Token> statementTokens){
        final List<TokenType> types = statementTokens.stream().map(Token::getType).collect(Collectors.toList());
        if (types.contains(TokenType.PRINT)) {
            return printParser;
        }
        else if (types.contains(TokenType.LET) && types.contains(TokenType.ASSIGN)){
            return declareAssignParser;
        }
        else if (types.contains(TokenType.LET)){
            return declareParser;
        }
        else {
            return assignParser;
        }
    }
}
