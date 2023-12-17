package publishingdemo;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.ParentClosePolicy;
import io.temporal.workflow.*;
import org.slf4j.Logger;
import publishingdemo.model.Document;

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
  public void edit(Document document) {
    logger.info("I am editing the document at URL: " + document.getUrl() + " in the main workflow");

    ChildWorkflowOptions childOptions =
        ChildWorkflowOptions.newBuilder()
            .setTaskQueue("child-workflow-task-queue")
            .setWorkflowId("child-workflow-task-queue-01")
            .setParentClosePolicy(ParentClosePolicy.PARENT_CLOSE_POLICY_ABANDON)
            .build();
    EditingWorkflow editingWorkflow =
        Workflow.newChildWorkflowStub(EditingWorkflow.class, childOptions);
    logger.info("I am starting the child workflow");

    Async.procedure(editingWorkflow::startChildWorkflow, document);
    Promise<WorkflowExecution> childExecution = Workflow.getWorkflowExecution(editingWorkflow);
    // Wait for child to start
    childExecution.get();
    // TODO Terminate the child workflow
  }

  @Override
  public void publish(Document document) {
    logger.info("I am publishing: " + document.getUrl() + " in the main workflow");
  }

  /** This is convenience signal to shut down the workflow */
  @Override
  public void exit() {
    logger.info("Exiting the publishing process");
    exit = true;
  }
}
