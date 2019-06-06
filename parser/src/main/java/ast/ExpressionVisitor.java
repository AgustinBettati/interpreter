package ast;

import ast.expression.ArithmeticExpression;
import ast.expression.Identifier;
import ast.expression.Literal;

public interface ExpressionVisitor {
    void visit(ArithmeticExpression arithmeticExpression);
    void visit(Identifier identifier);
    void visit(Literal literal);
}
