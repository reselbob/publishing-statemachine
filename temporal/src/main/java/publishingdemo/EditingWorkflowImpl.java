package publishingdemo;

import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowQueue;
import org.slf4j.Logger;
import publishingdemo.model.Document;

public class EditingWorkflowImpl implements EditingWorkflow {
    private static final Logger logger = Workflow.getLogger(PublishingWorkflowImpl.class);
    private final WorkflowQueue<Runnable> queue = Workflow.newWorkflowQueue(1024);
    private final boolean exit = false;
    @Override
    public void startWorkflow() {
        logger.info("Starting Workflow for Edit child workflow for Publishing use case");
        Workflow.await(() -> exit);
    }

    @Override
    public void copyEdit(Document document) {
        logger.info("I am copy editing the document");

    }

    @Override
    public void graphicEdit(Document document) {
        logger.info("I am graphic editing the document");
    }
}
