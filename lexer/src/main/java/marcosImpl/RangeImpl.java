package marcosImpl;


public class RangeImpl implements Range {
    private int first;
    private int second;

    public RangeImpl(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int getFirst() {
        return this.first;
    }

    @Override
    public int getSecond() {
        return this.second;
    }

    @Override
    public boolean equals(Object other) {
        RangeImpl other1 = (RangeImpl) other;
        return other1.first == first && other1.second == second;
    }

    @Override
    public String toString(){
        return "(" + first + "," + second + ")";
    }
}
