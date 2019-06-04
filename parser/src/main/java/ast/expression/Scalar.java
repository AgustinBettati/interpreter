package ast.expression;

import ast.Type;

public class Scalar {
    private final Type type;
    private final Object value;
    private final boolean isDefined;

    public Scalar(Type type, Object value){
        this.type = type;
        this.value = value;
        this.isDefined = true;
    }

    public Scalar(Type type){
        this.type = type;
        this.isDefined = false;
        this.value = null;
    }

    public String getStringValue() {
        return value.toString();
    }

    public Double getNumberValue() {
        return new Double(value.toString());
    }

    public Type getType() {
        return type;
    }

    public boolean isDefined() {
        return isDefined;
    }
}

