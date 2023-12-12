package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;

public class AbstractState {
    public static AbstractState editable;
    public static AbstractState graphicEdit;
    public static AbstractState copyEdit;
    public static AbstractState awaitingEdits;
    public static AbstractState awaitingPublish;
    public static AbstractState publish;
    public static AbstractState current;
    public void enter(){}
    public void update(AbstractEvent event){}
}
