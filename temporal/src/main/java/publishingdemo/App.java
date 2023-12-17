package publishingdemo;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import publishingdemo.model.Document;

public class App {
  static final String TASK_QUEUE = "PublishingDemo";

  private static final Logger logger = LoggerFactory.getLogger(App.class);

  @SuppressWarnings("CatchAndPrintStackTrace")
  public static void main(String[] args) throws MalformedURLException, InterruptedException {

    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the URL of the document that needs editing: ");
    String strUrl = scanner.nextLine();
    System.out.println("URL entered: " + strUrl);
    scanner.close();

    // create an arbitrary document
    URL url = new URL(strUrl);
    Document document = new Document(url);

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
    // client that can be used to start and signal workflows
    WorkflowClient client = WorkflowClient.newInstance(service);

    // Start the worker and hold onto the WorkerFactory for later use, if necessary.
    WorkerFactory factory = startWorkerWithFactory(client);

    // Declare the WORKFLOW_ID
    String WORKFLOW_ID = document.getId().toString();

    // now we can start running instances of our workflow - its state will be persisted
    WorkflowOptions options =
        WorkflowOptions.newBuilder()
            .setTaskQueue(TASK_QUEUE)
            .setWorkflowId(WORKFLOW_ID)
            // set the return options
            .setRetryOptions(
                RetryOptions.newBuilder()
                    .setInitialInterval(Duration.ofSeconds(1))
                    .setMaximumInterval(Duration.ofSeconds(10))
                    .build())
            .build();

    PublicationWorkflow wf = client.newWorkflowStub(PublicationWorkflow.class, options);
    try {

      WorkflowExecution exec = WorkflowClient.start(wf::startWorkflow, document);

    } catch (Exception ex) {
      // Just rethrow for now
      throw ex;
    }
  }

  /**
   * @param client, the workflow client
   * @return, the WorkerFactory that created the worker
   */
  private static WorkerFactory startWorkerWithFactory(WorkflowClient client) {
    // worker factory that can be used to create workers for specific task queues
    WorkerFactory factory = WorkerFactory.newInstance(client);

    // Worker that listens on a task queue and hosts both workflow and activity
    // implementations.
    Worker worker = factory.newWorker(TASK_QUEUE);

    // Workflows are stateful. So you need a type to create instances.
    worker.registerWorkflowImplementationTypes(PublicationWorkflowImpl.class);
    worker.registerActivitiesImplementations(new PublishingActivitiesImpl());

    // Start the worker created by this factory.
    factory.start();

    logger.info("Worker listening on task queue: {}.", TASK_QUEUE);

    return factory;
  }
}
