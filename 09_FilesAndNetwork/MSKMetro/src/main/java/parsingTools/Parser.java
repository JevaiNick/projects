package parsingTools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

abstract class Parser {
    String link;
    Document doc;
    protected Parser(String link) throws IOException {
        this.link = link;
        doc = Jsoup.connect(link).maxBodySize(0).get();
    }
    /*protected Document getDoc(String link) throws IOException {
        Document doc = Jsoup.connect(link).maxBodySize(0).get();
        return doc;
    }*/
}
