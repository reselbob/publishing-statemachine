package demo.pubstatemachine.queue;
import demo.pubstatemachine.message.AbstractMessage;

import java.util.concurrent.LinkedBlockingQueue;
public class SimpleMessageQueue {

    private final LinkedBlockingQueue<AbstractMessage> queue;

    public SimpleMessageQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public void putMessage(AbstractMessage message) {
        queue.offer(message);
    }

    public AbstractMessage getMessage() throws InterruptedException {
        return queue.take();
    }
}