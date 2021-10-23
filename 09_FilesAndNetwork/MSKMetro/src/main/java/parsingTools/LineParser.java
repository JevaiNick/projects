package parsingTools;

import core.Line;
import core.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineParser extends Parser {
    public LineParser(String link) throws IOException {
        super(link);
    }

    public List<Line> getLinesList() throws IOException {
        Elements linesElements = doc.select("span[data-line]");
        List<Line> linesList = new ArrayList<>();
        linesElements.forEach(element ->{
            Line line = new Line(element.attr("data-line"),element.text());
            linesList.add(line);
            });
        setStationListToLines(linesList);
        return linesList;
    }
    /*private void setStationListToLines(List<Line> linesList) throws IOException {
        Elements stationsElements = doc.select("p a[data-metrost]");

        Integer i = -1;
        for (Element element : stationsElements){
            String[] data = element.text().split("\\.",2);
            if (Integer.parseInt(data[0]) == 1){
                i++;
            }
            Station station = new Station(data[1],linesList.get(i));
            linesList.get(i).addStation(station);
        }
    }*/
    private void setStationListToLines(List<Line> linesList) throws IOException {
        //с помощью запроса выбираем нужные элементы из кода
        Elements stationsElements = doc.select("p a span[class]");

        Integer i = -1;
        for (Element element : stationsElements){

            Station station;
            //если номер станции стал "1" то это новая линия
            if (element.attr("class").equals("num") && element.text().replace('.',' ').trim().equals("1")){
                i++;
            }
            //записываем имя станции, взятое из атрибута элемента, и записываем в текущую станцию
            if (element.attr("class").equals("name")) {
                station = new Station(element.text().trim(), linesList.get(i));
                linesList.get(i).addStation(station);
            }
            //если следующая строка имеет атритбут "title", то добавляем переход в станцию
            if (element.hasAttr("title")){
                //ставим флаг, что существуют переходы
                linesList.get(i).getStations().get(linesList.get(i).getCountOfStations()).setHasConnection(true);
                //получаем номер линии на которую переход
                String numLine = element.attr("class").substring(element.attr("class").lastIndexOf("-") + 1);
                //получаем имя станции на которую переход
                String nameStation = element.attr("title").substring(element.attr("title").indexOf("«") + 1,
                        element.attr("title").indexOf("»"));
                //запоминаем в Map переходы
                linesList.get(i).getStations().get(linesList.get(i).getCountOfStations()).addConnection(nameStation, numLine);
            }
        }
    }
}
