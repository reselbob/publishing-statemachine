package publishingdemo;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import publishingdemo.model.Document;

public class NotifyGraphEdits {
  static final String TASK_QUEUE = "PublishingDemo";

  private static final Logger logger = LoggerFactory.getLogger(App.class);

  @SuppressWarnings("CatchAndPrintStackTrace")
  public static void main(String[] args) throws MalformedURLException, InterruptedException {
    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
    // client that can be used to start and signal workflows
    WorkflowClient client = WorkflowClient.newInstance(service);

    // Declare the WORKFLOW_ID
    String WORKFLOW_ID = args[0];

    PublishingWorkflow wf = client.newWorkflowStub(PublishingWorkflow.class, WORKFLOW_ID);
      URL url =
          new URL("https://learn.temporal.io/getting_started/#set-up-your-development-environment");
      Document document = new Document(url);

      wf.notifyGraphEditDone(document);
      System.exit(0);
  }

}
