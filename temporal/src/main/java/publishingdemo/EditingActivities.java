package publishingdemo;

@ActivitiesInterface
public interface EditingActivities {

    void sendCopyEdit(Document document);

    void sendGraphicEdit(Document document);

    Document mergeDocuments(Document copyEdit, Document graphicEdit);
}