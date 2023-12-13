package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;
import demo.pubstatemachine.queue.SimpleMessageQueue;

public class CopyEdit extends AbstractState{
    public CopyEdit(SimpleMessageQueue queue) {
        super(queue);
    }

    public void enter(){
        System.out.println("Now in the CopyEdit state");
    }

    public void update(AbstractEvent event){
        System.out.println(event.getEventType());
    }
}
