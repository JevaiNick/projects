import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    List<Station> route;
    List<Station> connections;
    RouteCalculator calculator;
    StationIndex stationIndex;
    @Override
    protected void setUp() throws Exception {
        route = new ArrayList<>();

       /* Line line1 = new Line(1,"First" );
        Line line2 = new Line(2,"Second");
        Line line3 = new Line(3,"Third");*/

        /*route.add(new Station("First st. on 1",line1));
        route.add(new Station("Second st. on 1",line1));
        route.add(new Station("First st. on 2",line2));
        route.add(new Station("Second st. on 2",line2));
        route.add(new Station("Third st. on 2",line2));
        route.add(new Station("First st. on 3",line3));
        route.add(new Station("Second st. on 3",line3));*/

        stationIndex = new StationIndex();

        stationIndex.addLine(new Line(1, "first"));
        stationIndex.addLine(new Line(2, "second"));
        stationIndex.addLine(new Line(3, "third"));

        stationIndex.addStation(new Station("first_1", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("first_2", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("first_3", stationIndex.getLine(1)));

        stationIndex.addStation(new Station("second_1", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("second_2", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("second_3", stationIndex.getLine(2)));

        stationIndex.addStation(new Station("third_1", stationIndex.getLine(3)));
        stationIndex.addStation(new Station("third_2", stationIndex.getLine(3)));
        stationIndex.addStation(new Station("third_3", stationIndex.getLine(3)));
        stationIndex.addStation(new Station("third_4", stationIndex.getLine(3)));
        stationIndex.addStation(new Station("third_5", stationIndex.getLine(3)));

        stationIndex.getLine(1).addStation(stationIndex.getStation("first_1"));
        stationIndex.getLine(1).addStation(stationIndex.getStation("first_2"));
        stationIndex.getLine(1).addStation(stationIndex.getStation("first_3"));

        stationIndex.getLine(2).addStation(stationIndex.getStation("second_1"));
        stationIndex.getLine(2).addStation(stationIndex.getStation("second_2"));
        stationIndex.getLine(2).addStation(stationIndex.getStation("second_3"));

        stationIndex.getLine(3).addStation(stationIndex.getStation("third_1"));
        stationIndex.getLine(3).addStation(stationIndex.getStation("third_2"));
        stationIndex.getLine(3).addStation(stationIndex.getStation("third_3"));
        stationIndex.getLine(3).addStation(stationIndex.getStation("third_4"));
        stationIndex.getLine(3).addStation(stationIndex.getStation("third_5"));

       List<Station> connectionStations = new ArrayList<>();
        connectionStations.add(stationIndex.getStation("third_2", 3));
        connectionStations.add(stationIndex.getStation("first_2", 1));
        stationIndex.addConnection(connectionStations);

        connectionStations.clear();

        connectionStations.add(stationIndex.getStation("third_4", 3));
        connectionStations.add(stationIndex.getStation("second_2", 2));
        stationIndex.addConnection(connectionStations);

        calculator = new RouteCalculator(stationIndex);
        route.addAll(Arrays.asList(stationIndex.getStation("first_1"), stationIndex.getStation("first_2"), stationIndex.getStation("third_2"),
                stationIndex.getStation("third_3"), stationIndex.getStation("third_4"), stationIndex.getStation("second_2"), stationIndex.getStation("second_3")));
        /*
                  third_1
                    |
                    |
  first_1----third_2\first_2----first_3
                    |
                    |
                  third_3
                    |
                    |
 second_1----third_4\second_2----second_3
                    |
                    |
                  third_5
*/

    }

    public void testcalculateDurationWithOneConnection(){
        double expected = 8.5;
        double actual = RouteCalculator.calculateDuration(route.subList(0,4));
        assertEquals(expected,actual);
    }
    public void testcalculateDurationWithTwoConnection(){
        double expected = 17;
        double actual = RouteCalculator.calculateDuration(route);
        assertEquals(expected,actual);
    }

    public void testgetShortestRoute(){


    }*/

}
