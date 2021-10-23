import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class RecursiveLinksParser extends RecursiveTask<List<String>> {

    private String link;
    private Document doc;
    private List<String> links = new ArrayList<>();

    public RecursiveLinksParser(String link) throws IOException {
        this.link = link;
        doc = Jsoup.connect(link).maxBodySize(0).get();
        fillLinksFromPage();
    }
    private void fillLinksFromPage() throws IOException {
        Elements elementsLinks = doc.select("a[href]");
        for (Element link : elementsLinks) {
            String url = link.absUrl("href");
            if (isValidURL(url) && url.contains(this.link) && !url.contains("#") && !url.equals(this.link) && !this.links.contains(url)){
                links.add(url);
            }

        }
    }
    public static boolean isValidURL(String urlString)
    {
        try
        {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception exception)
        {
            return false;
        }
    }



    @Override
    protected List<String>  compute() {
        List<String> urls = new ArrayList<>();
        urls.add(this.link);
        //System.out.println(this.link);
        List<RecursiveLinksParser> taskList = new ArrayList<>();
        for (String link : this.links){
            RecursiveLinksParser task = null;
            try {
                task = new RecursiveLinksParser(link);
            } catch (IOException e) {
                e.printStackTrace();
            }
            task.fork();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            taskList.add(task);
        }

        for (RecursiveLinksParser task : taskList){
            urls.addAll(task.join());
        }

        return urls;
    }
}
