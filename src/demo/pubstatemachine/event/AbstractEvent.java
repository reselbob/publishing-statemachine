package demo.pubstatemachine.event;

import demo.pubstatemachine.Document;

public class AbstractEvent{
    private final EventType eventType;
    private final Document document;

    public AbstractEvent(EventType eventType, Document document) {
        this.eventType = eventType;
        this.document = document;
    }
    public EventType getEventType() { return eventType;}
    public Document getDocument() { return document;}


}
