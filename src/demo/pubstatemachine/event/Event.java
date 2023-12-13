package demo.pubstatemachine.event;

import demo.pubstatemachine.Document;
public class Event extends AbstractEvent {
    public Event(EventType eventType, Document document) {
        super(eventType, document);
    }
}
