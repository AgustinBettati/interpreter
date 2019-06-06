import ast.*;
import ast.expression.ArithmeticExpression;
import ast.expression.Identifier;
import ast.expression.Literal;
import ast.expression.Scalar;
import ast.statement.*;
import errorhandler.ErrorHandler;

import java.util.Optional;

public class ExecutionVisitor implements ASTVisitor {

    private ExecutionContext ctx;
    private ExpressionEvaluatorVisitor expressionEvaluator;

    ExecutionVisitor(MessageEmitter emitter, ErrorHandler handler) {
        this.ctx = new ExecutionContext(emitter, handler);
        expressionEvaluator = new ExpressionEvaluatorVisitor(this.ctx);

    }

    @Override
    public void visit(Program program) {
        for (Statement statement : program.getStatements()) {
            statement.accept(this);
            if(!ctx.conforms()) return;
        }
    }

    @Override
    public void visit(EmptyNode empty) {
        ctx.reportViolation("[INTERPRETER] Program was parsed incorrectly");
    }

    @Override
    public void visit(AssignationStatement assignStatement) {
        if(!ctx.conforms()) return;
        final Identifier identifier = assignStatement.getIdentifier();
        if(!ctx.identifierIsDefined(identifier.getName())){
            ctx.reportViolation("[INTERPRETER] Variable "+ identifier.getName() + " was not declared", assignStatement.getInputRange());
        }
        else {
            final Scalar scalar = ctx.obtainIdentifierValue(identifier.getName());
            final Type identifierType = scalar.getType();

            expressionEvaluator.reset();
            assignStatement.getExpression().accept(expressionEvaluator);
            expressionEvaluator.getResult().ifPresent(result -> {
                if(result.getType() != identifierType)
                    ctx.reportViolation("[INTERPRETER] Variable " + identifier.getName() + " was assigned to invalid type", assignStatement.getInputRange());
                else
                    ctx.registerNewVariable(identifier.getName(), result);
            });
        }
    }

    @Override
    public void visit(DeclarationAssignationStatement declareAssignStatement) {
        if(!ctx.conforms()) return;
        final Identifier identifier = declareAssignStatement.getIdentifier();
        if(ctx.identifierIsDefined(identifier.getName()))
            ctx.reportViolation("[INTERPRETER] Variable was already been declared", declareAssignStatement.getInputRange());
        else{
            expressionEvaluator.reset();
            declareAssignStatement.getExpression().accept(expressionEvaluator);
            expressionEvaluator.getResult().ifPresent(value -> {
                if(declareAssignStatement.getType().getType() != value.getType())
                    ctx.reportViolation("[INTERPRETER] Expression does not conform to declared type", declareAssignStatement.getInputRange());
                else
                    ctx.registerNewVariable(identifier.getName(), value);
            });
        }
    }

    @Override
    public void visit(DeclarationStatement declareStatement) {
        if(!ctx.conforms()) return;
        final Identifier identifier = declareStatement.getIdentifier();
        if(ctx.identifierIsDefined(identifier.getName())){
            ctx.reportViolation("[INTERPRETER] Variable was already been declared", declareStatement.getInputRange());
        }
        else{
            ctx.registerNewVariable(identifier.getName(), new Scalar(declareStatement.getType().getType()));
        }
    }

    @Override
    public void visit(PrintStatement printStatement) {
        if(!ctx.conforms()) return;
        expressionEvaluator.reset();
        printStatement.getExpressionToPrint().accept(expressionEvaluator);
        expressionEvaluator.getResult().ifPresent(value -> {
            if(value.isDefined()){
                if(value.getType() == Type.STRING)
                    ctx.print(value.getStringValue());
                else
                    ctx.print(""+value.getNumberValue());
            }else
                ctx.reportViolation("[INTERPRETER] Undefined value",printStatement.getExpressionToPrint().getInputRange());
        });

    }

    @Override
    public void visit(ArithmeticExpression arithmeticExpression) {
        arithmeticExpression.accept(expressionEvaluator);
    }

    @Override
    public void visit(Identifier identifier) {
        identifier.accept(expressionEvaluator);
    }

    @Override
    public void visit(Literal literal) {
        literal.accept(expressionEvaluator);
    }
}
