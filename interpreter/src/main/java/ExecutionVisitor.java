import ast.*;
import ast.expression.ArithmeticExpression;
import ast.expression.Identifier;
import ast.expression.Literal;
import ast.expression.Scalar;
import ast.statement.*;
import errorhandler.ErrorHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExecutionVisitor implements ASTVisitor {

    private Map<String, Scalar> scopeVariables;
    private Scalar result;
    private MessageEmitter emitter;
    private ErrorHandler handler;
    private boolean conforms;

    public ExecutionVisitor(MessageEmitter emitter, ErrorHandler handler) {
        scopeVariables = new HashMap<>();
        this.emitter = emitter;
        this.handler = handler;
        this.conforms = true;
    }


    @Override
    public void visit(Program program) {
        for (Statement statement : program.getStatements()) {
            statement.accept(this);
            if(!conforms) return;
        }
    }

    @Override
    public void visit(EmptyNode empty) {
        handler.reportViolation("[PARSER] Program was parsed incorrectly");
        this.conforms = false;
    }

    @Override
    public void visit(AssignationStatement assignStatement) {
        if(!conforms) return;
        final Identifier identifier = assignStatement.getIdentifier();
        if(!scopeVariables.containsKey(identifier.getName())){
            handler.reportViolation("[PARSER] Variable "+ identifier.getName() + " was not declared", assignStatement.getInputRange());
            conforms = false;
        }
        else {
            final Scalar scalar = scopeVariables.get(identifier.getName());
            final Type identifierType = scalar.getType();

            assignStatement.getExpression().accept(this);
            final Scalar value = this.result;

            if(value.getType() != identifierType){
                handler.reportViolation("[PARSER] Variable " + identifier.getName() + " was assigned to invalid type", assignStatement.getInputRange());
                conforms = false;
            }
            else{
                scopeVariables.put(identifier.getName(), value);
            }
        }
    }

    @Override
    public void visit(DeclarationAssignationStatement declareAssignStatement) {
        if(!conforms) return;
        final Identifier identifier = declareAssignStatement.getIdentifier();
        if(scopeVariables.containsKey(identifier.getName())){
            handler.reportViolation("[PARSER] Variable was already been declared", declareAssignStatement.getInputRange());
            conforms = false;
        }
        else{
            declareAssignStatement.getExpression().accept(this);
            final Scalar value = this.result;

            if(declareAssignStatement.getType().getType() != value.getType()){
                handler.reportViolation("[PARSER] Expression does not conform to declared type", declareAssignStatement.getInputRange());
                conforms = false;
            }
            else{
                scopeVariables.put(identifier.getName(), value);
            }
        }
    }

    @Override
    public void visit(DeclarationStatement declareStatement) {
        if(!conforms) return;
        final Identifier identifier = declareStatement.getIdentifier();
        if(scopeVariables.containsKey(identifier.getName())){
            handler.reportViolation("[PARSER] Variable was already been declared", declareStatement.getInputRange());
            conforms = false;
        }
        else{
            scopeVariables.put(identifier.getName(), new Scalar(declareStatement.getType().getType()));
        }
    }

    @Override
    public void visit(PrintStatement printStatement) {
        if(!conforms) return;
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
            conforms = false;
        }
    }

    @Override
    public void visit(ArithmeticExpression arithmeticExpression) {
        if(!conforms) return;
        arithmeticExpression.getLeft().accept(this);
        Scalar leftValue = this.result;
        arithmeticExpression.getRight().accept(this);
        Scalar rightValue = this.result;
        final ArithmeticOperation operation = arithmeticExpression.getOperation();
        if(leftValue.getType() != rightValue.getType()){
            if(operation != ArithmeticOperation.ADDITION) {
                handler.reportViolation("[PARSER] Invalid arithmetic operation for given types", arithmeticExpression.getInputRange());
                conforms = false;
            }
            else {
                if(leftValue.getType() == Type.STRING)
                    this.result = new Scalar(Type.STRING,leftValue.getStringValue() + rightValue.getNumberValue());
                else
                    this.result = new Scalar(Type.NUMBER,leftValue.getNumberValue() + rightValue.getStringValue());
            }
        }
        else{
            if(leftValue.getType() == Type.NUMBER) { //son dos strings
                switch(operation){
                    case ADDITION:
                        this.result = new Scalar(Type.NUMBER, leftValue.getNumberValue() + rightValue.getNumberValue());
                        break;
                    case SUBSTRACTION:
                        this.result = new Scalar(Type.NUMBER, leftValue.getNumberValue() - rightValue.getNumberValue());
                        break;
                    case DIVISION:
                        this.result = new Scalar(Type.NUMBER, leftValue.getNumberValue() / rightValue.getNumberValue());
                        break;
                    case MULTIPLICATION:
                        this.result = new Scalar(Type.NUMBER, leftValue.getNumberValue() * rightValue.getNumberValue());
                        break;
                }
            }
            else{ //Son dos strings
                if (operation == ArithmeticOperation.ADDITION) {
                    this.result = new Scalar(Type.STRING, leftValue.getStringValue() + rightValue.getStringValue());
                } else {
                    handler.reportViolation("[PARSER] Cannot handle arithmetic operations in strings", arithmeticExpression.getInputRange());
                    conforms = false;
                }
            }
        }
    }

    @Override
    public void visit(Identifier identifier) {
        if(!conforms) return;
        if(scopeVariables.containsKey(identifier.getName())){
            this.result = scopeVariables.get(identifier.getName());
        }
        else {
            handler.reportViolation("[PARSER] Variable "+identifier.getName()+" was not defined", identifier.getInputRange());
            conforms = false;
        }
    }

    @Override
    public void visit(Literal literal) {
        if(!conforms) return;
        result = literal.getScalar();
    }
}
