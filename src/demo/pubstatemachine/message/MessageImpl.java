package demo.pubstatemachine.message;

import demo.pubstatemachine.Document;
public class MessageImpl extends AbstractMessage {
    public MessageImpl(MessageType messageType, Document document) {
        super(messageType, document);
    }
}
