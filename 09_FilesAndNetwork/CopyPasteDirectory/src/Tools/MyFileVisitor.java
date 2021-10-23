package Tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardCopyOption.*;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private Path pathTarget;
    private Path pathSource;

    public MyFileVisitor(Path pathSource, Path pathTarget){
        this.pathSource = pathSource;
        this.pathTarget = pathTarget;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {


            if (!file.toAbsolutePath().equals(pathTarget.toAbsolutePath())){
                Files.createDirectories(Paths.get(pathTarget + "\\" +
                        file.toAbsolutePath().toString().substring(file.toAbsolutePath().getNameCount(), file.toAbsolutePath().toString().lastIndexOf(File.separator))));
            }

            if (file.toFile().isFile()) {
            Files.copy(file, Paths.get(pathTarget + "\\" +
                    file.toAbsolutePath().toString().substring(file.toAbsolutePath().getNameCount(), file.toAbsolutePath().toString().lastIndexOf(File.separator))+
                    "\\"+ file.getFileName()),COPY_ATTRIBUTES );
        }


        return FileVisitResult.CONTINUE;
    }

}
