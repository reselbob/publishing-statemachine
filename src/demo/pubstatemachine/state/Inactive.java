package demo.pubstatemachine.state;

import demo.pubstatemachine.message.AbstractMessage;
import demo.pubstatemachine.queue.SimpleMessageQueue;

public class Inactive extends AbstractState{
    public Inactive(SimpleMessageQueue queue) {
        super(queue);
    }

    public void enter(){
        System.out.println("Now in the Inactive state");
    }

    public void update(AbstractMessage message){
        System.out.println(message.getEventType());
    }
}