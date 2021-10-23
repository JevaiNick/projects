import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static final String STAR_LINK = "https://secure-headland-59304.herokuapp.com/";

    public static void main(String[] args) {

        try {

            List<String> links = new ForkJoinPool().invoke(new RecursiveLinksParser(STAR_LINK));
            System.out.println("Threads done!");

            writingFile(links);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void writingFile(List<String> links) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name or file location");
        String fileName = scanner.nextLine();
        List<String> listForFile = new ArrayList<>();
        links.forEach(link -> {
            String tabStr = "";
            for (int i = 0; i < (tabCounting(link) - tabCounting(STAR_LINK)); i++) {
                tabStr += "\t";
            }
            listForFile.add(tabStr + link);
        });
        
        Path file = Paths.get(fileName + ".txt");
        Files.write(file, listForFile, Charset.forName("UTF-8"));
        System.out.println("File done!");
    }


    private static int tabCounting(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).equals("/")) {
                count++;
            }
        }
        return count;
    }
}
