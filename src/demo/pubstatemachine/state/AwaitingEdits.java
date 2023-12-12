package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;

public class AwaitingEdits extends AbstractState{
    public void enter(){
        System.out.println("Now in the AwaitingEdits state");
    }

    public void update(AbstractEvent event){
        System.out.println(event.getCode());
    }
}
