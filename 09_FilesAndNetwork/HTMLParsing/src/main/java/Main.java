import Validators.ImageValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

public class Main {
    public static final String PATH_TO_FOLDER = System.getProperty("user.dir") + "/HTMLParsing/images/";

    public static void main(String[] args)  {
        checkDirectory(PATH_TO_FOLDER);
        try {
            Document doc = Jsoup.connect("https://lenta.ru").get();
            Elements img = doc.getElementsByTag("img");
            img.forEach(element -> {
                String src = element.absUrl("src");
                try {
                    getImages(src);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private static void getImages(String src) throws IOException {

        int indexname = src.lastIndexOf("/") ;

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname);
        ImageValidator validator = new ImageValidator();
        if (validator.validate(name)){
            System.out.println(name);

            URL url = new URL(src);
            //InputStream in = url.openStream();
            try (InputStream in = url.openStream()){
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream( PATH_TO_FOLDER + name))){
                    in.transferTo(out);
                }
            }
        }

        //OutputStream out = new BufferedOutputStream(new FileOutputStream( PATH_TO_FOLDER + name));

        //in.transferTo(out);

        /*for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }*/
        //out.close();
        //in.close();

    }
    private static void checkDirectory(String path){
        File directory = new File(path);
        if (! directory.exists()) {
            directory.mkdir();
        }
    }
}
