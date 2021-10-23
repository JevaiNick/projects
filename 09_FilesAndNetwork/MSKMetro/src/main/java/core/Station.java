package core;

import java.util.HashMap;

public class Station
{
    private Line line;
    private String name;

    boolean hasConnection = false;

    public HashMap<String, String> getConnections() {
        return connections;
    }

    HashMap<String,String> connections = new HashMap<>();

    public Station(String name, Line line)
    {
        this.name = name;
        this.line = line;

    }
    public void addConnection(String station, String line){
        connections.put(station, line);
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    public Line getLine()
    {
        return line;
    }

    public String getName()
    {
        return name;
    }

    /*@Override
    public int compareTo(Station station)
    {
        int lineComparison = line.compareTo(station.getLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());
    }

    @Override
    public boolean equals(Object obj)
    {
        return compareTo((Station) obj) == 0;
    }*/

    @Override
    public String toString()
    {
        return name;
    }
}