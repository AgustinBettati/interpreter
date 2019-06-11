package marcosImpl;

import java.util.function.Supplier;

public class charSupplier implements Supplier<Character> {

    private String value;
    private int position;

    public charSupplier(String value) {
        this.value = value;
        this.position = 0;
    }

    @Override
    public Character get() {
        if (position < value.length()){
            value.charAt(position);
        }
        return null;
    }
}
