package demo.pubstatemachine.state;

import demo.pubstatemachine.Document;
import demo.pubstatemachine.StateMonitor;
import demo.pubstatemachine.message.AbstractMessage;
import demo.pubstatemachine.message.MessageImpl;
import demo.pubstatemachine.message.MessageType;
import demo.pubstatemachine.queue.SimpleMessageQueue;

public class Editable extends AbstractState {
    public Editable(SimpleMessageQueue queue) {
        super(queue);
    }

    public void enter() {
        System.out.println("Now in the Editable state");
    }

    @Override
    public void update(AbstractMessage message) throws InterruptedException {
        System.out.println(AbstractState.current.getClass().getSimpleName() + " is now updating " + message.getMessageType());
        Document document = message.getDocument();
        Thread.sleep(1000);
        queue.putMessage(new MessageImpl(MessageType.COMMAND_GRAPHIC_EDIT, document));
        queue.putMessage(new MessageImpl(MessageType.COMMAND_COPY_EDIT, document));
        StateMonitor sm = StateMonitor.getStateMonitor(message.getDocument());
        if(sm == null) {
            throw new NullPointerException(String.format("No State Monitor found in %s for update()",this.getClass().getSimpleName()));
        }
        sm.setEditable(true);
    }
}
