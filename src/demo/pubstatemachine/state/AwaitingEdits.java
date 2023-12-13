package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;
import demo.pubstatemachine.queue.SimpleMessageQueue;

public class AwaitingEdits extends AbstractState{
    public AwaitingEdits(SimpleMessageQueue queue) {
        super(queue);
    }

    public void enter(){
        System.out.println("Now in the AwaitingEdits state");
    }

    public void update(AbstractEvent event){
        // Collect both an
        System.out.println(event.getEventType());
    }
}
