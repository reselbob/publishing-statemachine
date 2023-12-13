package demo.pubstatemachine;

import demo.pubstatemachine.event.AbstractEvent;
import demo.pubstatemachine.event.*;
import demo.pubstatemachine.queue.SimpleMessageQueue;
import demo.pubstatemachine.state.*;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        SimpleMessageQueue queue = new SimpleMessageQueue();

        AbstractState.editable = new Editable(queue);
        AbstractState.graphicEdit = new GraphicEdit(queue);
        AbstractState.copyEdit = new CopyEdit(queue);
        AbstractState.awaitingEdits = new AwaitingEdits(queue);
        AbstractState.awaitingPublish = new AwaitingPublish(queue);
        AbstractState.publish = new Publish(queue);
        AbstractState.current = AbstractState.editable;

        URL url = new URL("https://learn.temporal.io/getting_started/#set-up-your-development-environment");
        Document document = new Document(url);

        while(true){

            AbstractState.current.enter();
            Event event = new
                    Event(MessageType.EVENT_EDITABLE, document);
            AbstractState.current.update(event);
        }
    }
}