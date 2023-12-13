package demo.pubstatemachine;
import java.net.URL;

public class Document {
    private final URL url;

    public Document(URL url) {
        this.url = url;
    }
    public URL getUrl() {
        return url;
    }
}
