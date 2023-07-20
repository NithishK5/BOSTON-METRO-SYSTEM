import java.util.ArrayList;

public class Connection extends Edge {
    int id;
    Station stationOne;
    Station stationTwo;

    public Connection(int connectionID, Station one, Station two) {
        id = connectionID;
        stationOne = one;
        stationTwo = two;
    }

    @Override
    public Station getNodeOne() {
        return stationOne;
    }

    @Override
    public Node getNodeTwo() {
        return stationTwo;
    }

    private ArrayList<Station> returnStations() {
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(stationOne);
        stations.add(stationTwo);
        return stations;
    }



}
