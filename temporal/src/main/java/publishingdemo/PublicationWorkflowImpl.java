package publishingdemo;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowQueue;
import java.time.Duration;
import org.slf4j.Logger;
import publishingdemo.model.Document;

public class PublicationWorkflowImpl implements PublicationWorkflow {
  private final PublishingActivities activities;

  private static final Logger logger = Workflow.getLogger(PublicationWorkflowImpl.class);
  private final WorkflowQueue<Runnable> queue = Workflow.newWorkflowQueue(1024);
  private boolean exit = false;
  private boolean copyEditComplete = false;
  private boolean graphicEditComplete = false;
  private boolean publishingComplete = false;

  public PublicationWorkflowImpl() {
    ActivityOptions options =
        ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofSeconds(10))
            .setTaskQueue("PublishingDemo")
            .build();
    this.activities = Workflow.newActivityStub(PublishingActivities.class, options);
  }

  @Override
  public void startWorkflow(Document document) {
    logger.info("Starting Workflow for Publishing");
    Promise<Void> copyEditPromise = Async.procedure(activities::copyEdit, document);
    Promise<Void> grppicEditPromise = Async.procedure(activities::graphicEdit, document);

    copyEditPromise.get();
    grppicEditPromise.get();

    if (copyEditPromise.isCompleted()) {
      logger.info("Copy edit complete");
      copyEditComplete = true;
    }
    if (grppicEditPromise.isCompleted()) {
      logger.info("Graphic edit complete");
      graphicEditComplete = true;
    }

    Workflow.await(() -> graphicEditComplete && copyEditComplete);
    Promise<Void> publishPromise = Async.procedure(activities::publish, document);
    publishPromise.get();

    if (publishPromise.isCompleted()) {
      logger.info("Publishing complete");
      publishingComplete = true;
    }
    Workflow.await(() -> copyEditComplete && graphicEditComplete && publishingComplete);
  }
}
