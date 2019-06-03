import ast.ASTVisitor;
import ast.EmptyNode;
import ast.Program;
import ast.Type;
import ast.expression.ArithmeticExpression;
import ast.expression.Identifier;
import ast.expression.Literal;
import ast.expression.Scalar;
import ast.statement.AssignationStatement;
import ast.statement.DeclarationAssignationStatement;
import ast.statement.DeclarationStatement;
import ast.statement.PrintStatement;
import errorhandler.ErrorHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExecutionVisitor implements ASTVisitor {

    private Map<String, Scalar> scopeVariables;
    private Scalar result;
    private MessageEmitter emitter;
    private ErrorHandler handler;

    public ExecutionVisitor(MessageEmitter emitter, ErrorHandler handler) {
        scopeVariables = new HashMap<>();
        this.emitter = emitter;
        this.handler = handler;
    }


    @Override
    public void visit(Program program) {
        program.getStatements().forEach(statement -> statement.accept(this));
    }

    @Override
    public void visit(EmptyNode empty) {
        handler.reportViolation("[PARSER] Program was parsed incorrectly");
    }

    @Override
    public void visit(AssignationStatement assignStatement) {
        final Identifier identifier = assignStatement.getIdentifier();
        if(!scopeVariables.containsKey(identifier.getName())){
            handler.reportViolation("[PARSER] Variable "+ identifier.getName() + " was not declared", assignStatement.getInputRange());
        }
        else {
            final Scalar scalar = scopeVariables.get(identifier.getName());
            final Type identifierType = scalar.getType();

            assignStatement.getExpression().accept(this);
            final Scalar value = this.result;

            if(value.getType() != identifierType){
                handler.reportViolation("[PARSER] Variable " + identifier.getName() + " was assigned to invalid type", assignStatement.getInputRange());
            }
            else{
                scopeVariables.put(identifier.getName(), value);
            }
        }
    }

    @Override
    public void visit(DeclarationAssignationStatement declareAssignStatement) {

    }

    @Override
    public void visit(DeclarationStatement declareStatement) {
        final Identifier identifier = declareStatement.getIdentifier();
        if(scopeVariables.containsKey(identifier.getName())){
            handler.reportViolation("[PARSER] Variable was already been declared", declareStatement.getInputRange());
        }
        else{
            scopeVariables.put(identifier.getName(), new Scalar(declareStatement.getType().getType()));
        }
    }

    @Override
    public void visit(PrintStatement printStatement) {
        printStatement.getExpressionToPrint().accept(this);
        final Scalar value = this.result;
        if(value.isDefined()){
            if(value.getType() == Type.STRING){
                emitter.print(value.getStringValue());
            }
            else{
                emitter.print(""+value.getNumberValue());
            }
        }else{
            handler.reportViolation("[PARSER] Undefined value",printStatement.getExpressionToPrint().getInputRange());
        }
    }

    @Override
    public void visit(ArithmeticExpression arithmeticExpression) {


    }

    @Override
    public void visit(Identifier identifier) {
        if(scopeVariables.containsKey(identifier.getName())){
            this.result = scopeVariables.get(identifier.getName());
        }
        else {
            handler.reportViolation("[PARSER] Variable "+identifier.getName()+" was not defined", identifier.getInputRange());
        }
    }

    @Override
    public void visit(Literal literal) {
        result = literal.getScalar();
    }
}
