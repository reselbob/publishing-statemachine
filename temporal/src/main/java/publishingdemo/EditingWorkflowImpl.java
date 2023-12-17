package publishingdemo;

import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowQueue;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import publishingdemo.model.Document;

public class EditingWorkflowImpl implements EditingWorkflow {
  private static final Logger logger = Workflow.getLogger(EditingWorkflowImpl.class);
  private final WorkflowQueue<Runnable> queue = Workflow.newWorkflowQueue(1024);
  private boolean exit = false;

  /**
   * This is the main workflow method that will be invoked by the parent workflow. It creates two
   * promises that will be executed asynchronously in parallel. The functions called are copyEdit
   * and graphicEdit. The exit method is called to shut down the workflow.
   *
   * @param document, the document to edit
   */
  @Override
  public void startChildWorkflow(Document document) {
    logger.info(
        "Starting Workflow for Edit child workflow for Publishing use case for document: "
            + document.getUrl());
      Promise<Void> copyEdit = Async.procedure(()->copyEdit(document));
      Promise<Void> graphicEdit = Async.procedure(()->graphicEdit(document));
      copyEdit.get();
      graphicEdit.get();

    Workflow.await(() -> exit);
  }

  @Override
  public void copyEdit(Document document){
    logger.info("I am copy editing the document: " + document.getUrl() + " in the child workflow");
  }

  @Override
  public void graphicEdit(Document document)  {
    logger.info("I am graphic editing the document: " + document.getUrl() + " in the child workflow");
  }

 // @Override
  public void exit() {
    logger.info("Exiting the editing process");
    exit = true;
  }
}
