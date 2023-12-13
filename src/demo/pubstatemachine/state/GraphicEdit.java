package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;
import demo.pubstatemachine.queue.SimpleMessageQueue;

public class GraphicEdit extends AbstractState {
    public GraphicEdit(SimpleMessageQueue queue) {
        super(queue);
    }

    public void enter() {
        System.out.println("Now in the GraphicEdit state");
    }

    public void update(AbstractEvent event) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(event.getEventType());
    }
}
