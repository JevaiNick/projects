import core.Line;

import parsingTools.LineParser;

import java.io.IOException;
import java.util.List;

public class Main {
    static final String LINK = "https://www.moscowmap.ru/metro.html#lines";
    static final String PLACE_FOR_JSON = "mskMetro.json";
    public static void main(String[] args)  {

        try {
            List<Line> linesList = new LineParser(LINK).getLinesList();
            showCountOfStationsOnLines(linesList);

            JsonCreator jsonCreator = new JsonCreator();
            jsonCreator.createJSONMap(linesList,PLACE_FOR_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showCountOfStationsOnLines(List<Line> linesList) {
        linesList.forEach(line -> {
            System.out.println(line.getNumber() + ": " + line.getStations().size() );
        });
    }
}
