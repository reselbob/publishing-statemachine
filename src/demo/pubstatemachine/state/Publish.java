package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;

public class Publish extends AbstractState{
    public void enter(){
        System.out.println("Now in the Publish state");
    }

    public void update(AbstractEvent event){
        System.out.println(event.getCode());
    }
}
