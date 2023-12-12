package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;

public class GraphicEdit extends AbstractState{
    public void enter(){
        System.out.println("Now in the GraphicEdit state");
    }

    public void update(AbstractEvent event){
        System.out.println(event.getCode());
    }
}
