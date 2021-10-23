import Tools.MyFileVisitor;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.Scanner;

import static java.nio.file.FileVisitResult.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path to source:");
        String pathFromStr = scanner.nextLine();
        Path pathFrom = Paths.get(pathFromStr);
        System.out.println("Enter the path to new place:");
        String pathToStr = scanner.nextLine();
        Path pathTo = Paths.get(pathToStr);
        MyFileVisitor myFileVisitor = new MyFileVisitor(pathFrom, pathTo);
        try {
            Files.walkFileTree(pathFrom, myFileVisitor);


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
