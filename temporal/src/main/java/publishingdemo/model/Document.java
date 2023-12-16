package publishingdemo.model;

import java.net.URL;

public class Document {
  private URL url;

  public Document() {}

  public Document(URL url) {
    this.url = url;
  }

  public URL getUrl() {
    return url;
  }
}
