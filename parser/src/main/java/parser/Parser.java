package parser;

import ast.ASTNode;
import errorhandler.ErrorHandler;

public interface Parser {
    ASTNode parse(String src, ErrorHandler handler);

}
