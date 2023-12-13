package demo.pubstatemachine.event;

import demo.pubstatemachine.Document;

public class AbstractEvent{
    private final MessageType messageType;
    private final Document document;

    public AbstractEvent(MessageType messageType, Document document) {
        this.messageType = messageType;
        this.document = document;
    }
    public MessageType getEventType() { return messageType;}
    public Document getDocument() { return document;}


}
