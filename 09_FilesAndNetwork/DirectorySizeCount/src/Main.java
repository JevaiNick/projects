import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path to file:");
        String path = scanner.nextLine();
        System.out.print("Directory \"" + path + "\" size :");
        try {
            showSizeInReadableStyle(getFolderSize(path));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private static long getFolderSize(String path) throws IOException {
        Path folder = Paths.get(path);
        long size = Files.walk(folder)
                .filter(p -> p.toFile().isFile())
                .mapToLong(p -> p.toFile().length())
                .sum();
        return size;
    }
    private static void showSizeInReadableStyle(long size){
        String[]units = new String[]{ "B", "KB", "MB", "GB", "TB" };
        int unitIndex = (int) (Math.log10(size)/3);
        double unitValue = 1 << 10 * (unitIndex);

        String readableSize = new DecimalFormat("#,##0.#")
                .format(size/unitValue) + " "
                + units[unitIndex];
        System.out.printf(readableSize + "%n");
    }
}
