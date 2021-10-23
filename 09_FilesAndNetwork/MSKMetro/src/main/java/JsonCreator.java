import core.Line;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonCreator {
    private JSONObject createJSONData(JSONObject stations, JSONArray lines, JSONArray connections)
    {
        JSONObject jsonData = new JSONObject();
        jsonData.put("lines", lines);
        jsonData.put("stations", stations);
        jsonData.put("connections", connections);
        return jsonData;
    }

    private void writeInJSONMap(JSONObject jsonData, String placeToPut){
        try (FileWriter file = new FileWriter(placeToPut)) {

            file.write(jsonData.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONArray createConnections(List<Line> linesList){
        JSONArray connections = new JSONArray();

        linesList.forEach(line -> {

            line.getStations().forEach(station -> {

                if (station.isHasConnection()) {
                    JSONArray connection = new JSONArray();
                    JSONObject connectedStations = new JSONObject();
                    connectedStations.put("line", station.getLine().getNumber());
                    connectedStations.put("station", station.getName());
                    connection.add(connectedStations);

                    for (Map.Entry<String, String> entry:
                         station.getConnections().entrySet()) {
                        connectedStations = new JSONObject();
                        connectedStations.put("line", entry.getValue());
                        connectedStations.put("station", entry.getKey());
                        connection.add(connectedStations);
                    }
                    connections.add(connection);
                }
            });
        });
        return connections;
    }

    private JSONObject createStations(List<Line> linesList){
        JSONObject stations = new JSONObject();
        linesList.forEach(line -> {
            JSONArray stationsArray = new JSONArray();

            line.getStations().forEach(station -> {
                stationsArray.add(station.getName().trim());
            });
            stations.put(line.getNumber(),stationsArray);
        });
        return stations;
    }

    private JSONArray createLines(List<Line> linesList){
        JSONArray lines = new JSONArray();
        linesList.forEach(line -> {
            JSONObject lineJSON = new JSONObject();
            lineJSON.put("number", line.getNumber());
            lineJSON.put("name", line.getName());
            lines.add(lineJSON);
        });
        return lines;
    }

    public void createJSONMap(List<Line> linesList, String placeToPut){
        JSONObject jsonData = createJSONData(createStations(linesList),createLines(linesList), createConnections(linesList));
        writeInJSONMap(jsonData,placeToPut);
    }
}
