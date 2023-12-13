package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;
import demo.pubstatemachine.queue.SimpleMessageQueue;

public class AbstractState {
    // add a constructor
    public AbstractState(SimpleMessageQueue queue){
        AbstractState.queue = queue;
    }

    private static SimpleMessageQueue queue;
    public static AbstractState editable;
    public static AbstractState graphicEdit;
    public static AbstractState copyEdit;
    public static AbstractState awaitingEdits;
    public static AbstractState awaitingPublish;
    public static AbstractState publish;
    public static AbstractState current;
    public void enter(){}
    public void update(AbstractEvent event) throws InterruptedException {}
}
