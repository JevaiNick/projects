import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RegionGeneration implements Runnable {
    char[] letters;
    String path;
    int regionCode;

    public RegionGeneration(char[] letters, String path, int regionCode) {
        this.letters = letters;
        this.path = path + "number" + regionCode + ".txt";
        this.regionCode = regionCode;
    }

    @Override
    public void run() {

        try (FileWriter writer = new FileWriter(path,true)) {

            StringBuilder stringBuilder = new StringBuilder();
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            stringBuilder.append(firstLetter);
                            stringBuilder.append(padNumber(number, 2));
                            stringBuilder.append(secondLetter);
                            stringBuilder.append(thirdLetter);
                            stringBuilder.append(padNumber(regionCode, 2));
                            stringBuilder.append("\n");

                        }
                    }
                }
            }
            writer.write(stringBuilder.toString());
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        StringBuilder numberToReturn = new StringBuilder();
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberToReturn.append("0");
        }
        numberToReturn.append(numberStr);
        return numberToReturn.toString();
    }
}
