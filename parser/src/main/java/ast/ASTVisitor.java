package ast;

import ast.expression.ArithmeticExpression;
import ast.expression.Identifier;
import ast.expression.Literal;
import ast.statement.AssignationStatement;
import ast.statement.DeclarationAssignationStatement;
import ast.statement.DeclarationStatement;
import ast.statement.PrintStatement;

public interface ASTVisitor extends ExpressionVisitor {
    void visit(Program program);
    void visit(EmptyNode empty);

    void visit(AssignationStatement assignStatement);
    void visit(DeclarationAssignationStatement declareAssignStatement);
    void visit(DeclarationStatement declareStatement);
    void visit(PrintStatement printStatement);
}
