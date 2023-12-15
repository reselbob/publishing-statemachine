package demo.pubstatemachine;

import demo.pubstatemachine.message.AbstractMessage;
import demo.pubstatemachine.message.MessageImpl;
import demo.pubstatemachine.message.MessageType;

import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        Controller controller = new Controller();
        URL url = new URL("https://learn.temporal.io/getting_started/#set-up-your-development-environment");
        Document document = new Document(url);
        AbstractMessage message = new MessageImpl(MessageType.EVENT_EDITABLE, document);
        System.out.println("Client: Sending event: " + message.getMessageType());

        controller.sendMessage(message);
        System.out.println("Client: Sent event to controller: " + message.getMessageType());

        //event = new AbstractEvent(MessageType.COMMAND_GRAPHIC_EDIT, document);
        //controller.sendMessage(event);
        //System.out.println("Client: Sent event to controller: " + event.getEventType());
    }
}
