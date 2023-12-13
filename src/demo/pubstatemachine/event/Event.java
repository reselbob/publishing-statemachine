package demo.pubstatemachine.event;

import demo.pubstatemachine.Document;
public class Event extends AbstractEvent {
    public Event(MessageType messageType, Document document) {
        super(messageType, document);
    }
}
