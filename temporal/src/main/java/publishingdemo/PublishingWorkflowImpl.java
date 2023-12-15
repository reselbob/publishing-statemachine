package publishingdemo;


import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowQueue;
import publishingdemo.model.Document;
import org.slf4j.Logger;
import java.util.List;


public class PublishingWorkflowImpl implements PublishingWorkflow {

    private static final Logger logger = Workflow.getLogger(PublishingWorkflowImpl.class);
    private final WorkflowQueue<Runnable> queue = Workflow.newWorkflowQueue(1024);
    private boolean exit = false;
    @Override
    public void startWorkflow() {
        logger.info("Starting Workflow for Publishing use case");
        Workflow.await(() -> exit);
    }

    @Override
    public void edit(Document document) {
    }

    @Override
    public void publish(Document document) {
    }
    /** This is convenience signal to shut down the workflow */
    @Override
    public void exit() {
        logger.info("Exiting the shopping cart");
        exit = true;
    }
}
