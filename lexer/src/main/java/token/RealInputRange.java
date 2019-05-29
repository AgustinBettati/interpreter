package token;

import javafx.util.Pair;

public class RealInputRange implements InputRange {

    private Integer startLine;
    private Integer startColumn;
    private Integer endLine;
    private Integer endColumn;

    public RealInputRange(Integer startLine, Integer startColumn, Integer endLine, Integer endColumn) {
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public RealInputRange() {
        this.startLine = 1;
        this.startColumn = 1;
        this.endLine = 1;
        this.endColumn = 0;
    }

    public RealInputRange startFromNextPosition() {
        return new RealInputRange(endLine,endColumn + 1,endLine,endColumn);
    }

    public RealInputRange moveColumn(int amt) {
        return new RealInputRange(startLine,startColumn,endLine,endColumn + amt);
    }

    public RealInputRange moveLine() {
        return new RealInputRange(startLine,startColumn,endLine + 1,1);
    }

    @Override
    public Integer getStartLine() {
        return startLine;
    }

    @Override
    public Integer getStartColumn() {
        return startColumn;
    }

    @Override
    public Integer getEndLine() {
        return endLine;
    }

    @Override
    public Integer getEndColumn() {
        return endColumn;
    }
}
