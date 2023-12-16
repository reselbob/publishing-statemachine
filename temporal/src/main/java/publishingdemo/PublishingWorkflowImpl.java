package publishingdemo;

import io.temporal.workflow.*;
import publishingdemo.model.Document;
import org.slf4j.Logger;

public class PublishingWorkflowImpl implements PublishingWorkflow {

    private static final Logger logger = Workflow.getLogger(PublishingWorkflowImpl.class);
    private final WorkflowQueue<Runnable> queue = Workflow.newWorkflowQueue(1024);
    private boolean exit = false;



    @Override
    public void startWorkflow() {
        logger.info("Starting Workflow for Publishing");
        Workflow.await(() -> exit);
    }

    @Override
    public void edit(Document document) throws InterruptedException {
        logger.info("I am editing the document at URL: " + document.getUrl() + " in the main workflow");
        ChildWorkflowOptions childOptions =
                ChildWorkflowOptions.newBuilder()
                        .setTaskQueue("child-workflow-task-queue")
                        .setWorkflowId("child-workflow-task-queue-01")
                        .build();
        EditingWorkflow editingWorkflow = Workflow.newChildWorkflowStub(EditingWorkflow.class, childOptions);
        editingWorkflow.startWorkflow(document);
    }

    @Override
    public void publish(Document document) throws InterruptedException {
        logger.info("I am publishing: " + document.getUrl() + " in the main workflow");
        Thread.sleep(1000);
    }
    /** This is convenience signal to shut down the workflow */
    @Override
    public void exit() {
        logger.info("Exiting the publishing process");
        exit = true;
    }
}
