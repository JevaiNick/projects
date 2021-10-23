import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable{
    File file;
    String dstFolder;
    long start;

    public ImageResizer(File file, String dstFolder, long start) {
        this.file = file;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run() {
        try
        {

                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    return;
                }

                int newWidth = 300;
                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = Scalr.resize(image, newWidth, newHeight);


                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);


                System.out.printf("%s finished in %d  \n", Thread.currentThread().getName(),System.currentTimeMillis() - start);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
