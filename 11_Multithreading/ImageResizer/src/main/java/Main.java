import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args)
    {
        String srcFolder = "C:/Users/Jevai/Desktop/src";
        String dstFolder = "C:/Users/Jevai/Desktop/dst";

        File srcDir = new File(srcFolder);

        File[] files = srcDir.listFiles();



        int coreCount = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(coreCount);
        long start = System.currentTimeMillis();
        System.out.println("Core count: " + coreCount);
        /*List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < coreCount; i++){

            if (i == coreCount - 1){
                files1 = new File[files.length - (files.length / coreCount) * i];
            }else {
                files1 = new File[files.length / coreCount];
            }
            System.arraycopy(files, (files.length / coreCount) * i, files1, 0, files1.length );
            ImageResizer imageResizer1 = new ImageResizer(files1,dstFolder, start);
            threads.add(new Thread(imageResizer1));
        }

        threads.forEach(thread -> thread.start());
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/
        Arrays.stream(files).forEach(file -> {
            ImageResizer imageResizer = new ImageResizer(file, dstFolder, start);
            executor.submit(imageResizer);
        });
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Total time: " + (System.currentTimeMillis() - start));
    }
}
