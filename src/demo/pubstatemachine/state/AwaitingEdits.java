package demo.pubstatemachine.state;


import demo.pubstatemachine.Document;
import demo.pubstatemachine.StateMonitor;
import demo.pubstatemachine.message.AbstractMessage;
import demo.pubstatemachine.message.MessageImpl;
import demo.pubstatemachine.message.MessageType;
import demo.pubstatemachine.queue.SimpleMessageQueue;

public class AwaitingEdits extends AbstractState{
    public AwaitingEdits(SimpleMessageQueue queue) {
        super(queue);
    }

    public void enter(){
        System.out.println("Now in the AwaitingEdits state");
    }

    public void update(AbstractMessage message) throws InterruptedException {
        System.out.println(AbstractState.current.getClass().getSimpleName() + " is now updating " + message.getEventType());
        Document document = message.getDocument();
        Thread.sleep(1000);
        StateMonitor sm = StateMonitor.getStateMonitor(message.getDocument());
        if(sm == null) {
            throw new NullPointerException(String.format("No State Monitor found in %s for update()",this.getClass().getSimpleName()));
        }

        if(message.getEventType() == MessageType.EVENT_COPY_EDITABLE){
            System.out.println("Doing copy edit behavior");
            sm.setCopyEdited(true);
        }

        if(message.getEventType() == MessageType.EVENT_GRAPHIC_EDITABLE){
            System.out.println("Doing graphic edit behavior");
            sm.setGraphicEdited(true);
        }

        if(sm.isGraphicEdited() && sm.isCopyEdited()){
            queue.putMessage(new MessageImpl(MessageType.COMMAND_PUBLISH, document));
        }
    }
}
