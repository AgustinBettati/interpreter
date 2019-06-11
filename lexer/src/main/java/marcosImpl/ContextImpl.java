package marcosImpl;

import java.util.ArrayList;
import java.util.List;

public class ContextImpl implements Context {


    private int actualColumn;
    private int actualRow;
    private int initialRow;
    private int initialColumn;
    private Range columnRange;
    private Range rowRange;
    private List<TokenMarcos> result;

    public ContextImpl(int actualColumn, int actualRow, int initialRow, int initialColumn, Range columnRange, Range rowRange, List<TokenMarcos> result) {
        this.actualColumn = actualColumn;
        this.actualRow = actualRow;
        this.initialRow = initialRow;
        this.initialColumn = initialColumn;
        this.columnRange = columnRange;
        this.rowRange = rowRange;
        this.result = result;
    }

    public ContextImpl(int actualColumn, int actualRow, int initialRow, int initialColumn) {
        this.actualColumn = actualColumn;
        this.actualRow = actualRow;
        this.initialRow = initialRow;
        this.initialColumn = initialColumn;
        this.columnRange= new RangeImpl(initialColumn,actualColumn);
        this.rowRange = new RangeImpl(initialRow, actualRow);
        this.result = new ArrayList<TokenMarcos>();
    }

    @Override
    public int getActualColumn() {
        return this.actualColumn;
    }

    @Override
    public int getActualRow() {
        return this.actualRow;
    }

    @Override
    public int getInitialRow() {
        return this.initialRow;

    }

    @Override
    public int getInitialColumn() {
        return this.initialColumn;
    }

    @Override
    public Range getColumnRange() {
        return new RangeImpl(initialColumn,actualColumn);
    }

    @Override
    public Range getRowRange() {
        return new RangeImpl(initialRow,actualRow);
    }

    @Override
    public void addToken(TokenMarcos tokenMarcos) {
        result.add(tokenMarcos);
    }

    public void setActualColumn(int actualColumn) {
        this.actualColumn = actualColumn;
    }

    public void setActualRow(int actualRow) {
        this.actualRow = actualRow;
    }

    public void setInitialRow(int initialRow) {
        this.initialRow = initialRow;
    }

    public void setInitialColumn(int initialColumn) {
        this.initialColumn = initialColumn;
    }

    public void setColumnRange(Range columnRange) {
        this.columnRange = columnRange;
    }

    public void setRowRange(Range rowRange) {
        this.rowRange = rowRange;
    }

    public List<TokenMarcos> getResult() {
        return result;
    }

    @Override
    public void nextRow() {
        this.actualRow+=1;
        this.actualColumn=0;
    }

    @Override
    public void nextColumn() {
        this.actualColumn+=1;
    }

    @Override
    public void restart() {
        this.initialColumn = this.actualColumn;
        this.initialRow = this.actualRow;
    }

    public void setResult(List<TokenMarcos> result) {
        this.result = result;
    }
}
