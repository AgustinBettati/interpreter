import ast.ArithmeticOperation;
import ast.ExpressionVisitor;
import ast.Type;
import ast.expression.ArithmeticExpression;
import ast.expression.Identifier;
import ast.expression.Literal;
import ast.expression.Scalar;
import errorhandler.ErrorHandler;

import java.util.Map;
import java.util.Optional;

public class ExpressionEvaluatorVisitor implements ExpressionVisitor {

    private ExecutionContext ctx;
    private Optional<Scalar> result;

    ExpressionEvaluatorVisitor(ExecutionContext ctx) {
        this.ctx = ctx;
        this.result = Optional.empty();
    }

    @Override
    public void visit(ArithmeticExpression arithmeticExpression) {
        if(!ctx.conforms()) return;
        arithmeticExpression.getLeft().accept(this);
        Optional<Scalar> leftOpt = this.result;
        arithmeticExpression.getRight().accept(this);
        Optional<Scalar> rightOpt = this.result;
        final ArithmeticOperation operation = arithmeticExpression.getOperation();
        if(leftOpt.isPresent() && rightOpt.isPresent()) {
            Scalar leftValue= leftOpt.get();
            Scalar rightValue= rightOpt.get();
            if (leftValue.getType() != rightValue.getType()) {
                if (operation != ArithmeticOperation.ADDITION) {
                    ctx.reportViolation("[INTERPRETER] Invalid arithmetic operation for given types", arithmeticExpression.getInputRange());
                } else {
                    if (leftValue.getType() == Type.STRING)
                        this.result = Optional.of(new Scalar(Type.STRING, leftValue.getStringValue() + rightValue.getNumberValue()));
                    else
                        this.result = Optional.of(new Scalar(Type.NUMBER, leftValue.getNumberValue() + rightValue.getStringValue()));
                }
            } else {
                if (leftValue.getType() == Type.NUMBER) { //son dos strings
                    switch (operation) {
                        case ADDITION:
                            this.result = Optional.of(new Scalar(Type.NUMBER, leftValue.getNumberValue() + rightValue.getNumberValue()));
                            break;
                        case SUBSTRACTION:
                            this.result = Optional.of(new Scalar(Type.NUMBER, leftValue.getNumberValue() - rightValue.getNumberValue()));
                            break;
                        case DIVISION:
                            this.result = Optional.of(new Scalar(Type.NUMBER, leftValue.getNumberValue() / rightValue.getNumberValue()));
                            break;
                        case MULTIPLICATION:
                            this.result = Optional.of(new Scalar(Type.NUMBER, leftValue.getNumberValue() * rightValue.getNumberValue()));
                            break;
                    }
                } else { //Son dos strings
                    if (operation == ArithmeticOperation.ADDITION) {
                        this.result = Optional.of(new Scalar(Type.STRING, leftValue.getStringValue() + rightValue.getStringValue()));
                    } else {
                        ctx.reportViolation("[INTERPRETER] Cannot handle arithmetic operations in strings", arithmeticExpression.getInputRange());
                    }
                }
            }
        }
    }

    @Override
    public void visit(Identifier identifier) {
        if(!ctx.conforms()) return;
        if(ctx.identifierIsDefined(identifier.getName())){
            this.result = Optional.of(ctx.obtainIdentifierValue(identifier.getName()));
        }
        else {
            ctx.reportViolation("[INTERPRETER] Variable "+identifier.getName()+" was not defined", identifier.getInputRange());
        }
    }

    @Override
    public void visit(Literal literal) {
        if(!ctx.conforms()) return;
        result = Optional.of(literal.getScalar());
    }

    public void reset() {
        this.result = Optional.empty();
    }
    public Optional<Scalar> getResult() {
        return result;
    }
}
