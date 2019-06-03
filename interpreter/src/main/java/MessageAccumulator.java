import java.util.ArrayList;
import java.util.List;

public class MessageAccumulator implements MessageEmitter{

    private List<String> messages = new ArrayList<>();

    @Override
    public void print(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
