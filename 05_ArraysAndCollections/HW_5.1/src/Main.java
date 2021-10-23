import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Task1
        String text = "Каждый охотник желает знать, где сидит фазан.";
        String[] colorWords = text.split(",?\\s+");
        //т.к. у класса Array нет метода reverse, то используем Collections
        Collections.reverse(Arrays.asList(colorWords));
        for (String item: colorWords)
        {
            System.out.print(item + " ");
        }
        System.out.println("=================================================");

        //Task2
        final int COUNT_OF_PATIENTS = 30;
        final float MIN_TEMP = 32;
        final float MAX_TEMP = 40;
        final float MIN_HEALTH_TEMP = (float) 36.2;
        final float MAX_HEALTH_TEMP = (float) 36.9;

        float[] tempOfPatients = new float[COUNT_OF_PATIENTS];
        float avgTemp = 0;
        int numberOfHealthy = 0;
        System.out.println("Температуры пациентов: ");
        for (float item:tempOfPatients) {
            item = MIN_TEMP + (float)((MAX_TEMP - MIN_TEMP) * Math.random());
            System.out.print(item + "; ");
            avgTemp += item;
            if (item >= MIN_HEALTH_TEMP && item <= MAX_HEALTH_TEMP)
            {
                numberOfHealthy++;
            }
        }
        avgTemp /= tempOfPatients.length;
        System.out.println("\nСредняя температура пациентов: " + avgTemp);
        System.out.println("Количество здоровых пациентов: " + numberOfHealthy);
        System.out.println("=================================================");

        //Task*
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размеры массива:");
        System.out.print("высота: ");
        int height = scanner.nextInt();
        System.out.println("ширина: ");
        int width = scanner.nextInt();
        String[][] mainArray = new String[height][width];
        for (int i = 0; i < height; i++ )
        {
            for (int j = 0; j < width; j++)
            {
                if (i == j || j == (width - 1 - i))
                {
                    mainArray[i][j] = "X";
                }else
                {
                    mainArray[i][j] = " ";
                }
                System.out.print(mainArray[i][j]);
            }
            System.out.println();
        }
    }
}
