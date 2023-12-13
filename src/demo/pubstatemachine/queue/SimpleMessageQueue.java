package demo.pubstatemachine.queue;
import demo.pubstatemachine.event.AbstractEvent;
import java.util.concurrent.LinkedBlockingQueue;
public class SimpleMessageQueue {

    private final LinkedBlockingQueue<AbstractEvent> queue = new LinkedBlockingQueue<>();

    public void putMessage(AbstractEvent message) {
        queue.offer(message);
    }

    public AbstractEvent getMessage() throws InterruptedException {
        return queue.take();
    }
}