package pharma.lms.PharmaLMS.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MessageConsumer {
    private static List<Message> messages = new ArrayList<>();

    @JmsListener(destination = "pharmaqueue")
    public void receiveMessage(Message message) {
        messages.add(message);

        log.info("\nmessage: " + message.getContent() + "\nto: " + message.getRecipientId());
    }

    public static List<Message> getMessages() {
        return messages;
    }
}
