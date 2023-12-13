package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;
import demo.pubstatemachine.queue.SimpleMessageQueue;

public class Publish extends AbstractState{
    public Publish(SimpleMessageQueue queue) {
        super(queue);
    }

    public void enter(){
        System.out.println("Now in the Publish state");
    }

    public void update(AbstractEvent event){
        System.out.println(event.getEventType());
    }
}
