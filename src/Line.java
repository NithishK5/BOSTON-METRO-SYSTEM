/* Optional line class
 */

import java.util.ArrayList;
import java.util.Collections;

public class Line {
    int id;
    String name;
    ArrayList<Station> stationList;
    ArrayList<Connection> connectionList;

    public Line(int lineID, String lineName) {//, ArrayList<Station>  lineStationList, ArrayList<Connection> lineConnectionList) {
        id = lineID;
        name = lineName;
        stationList = new ArrayList<>();
        connectionList = new ArrayList<>();
        //stationList = lineStationList;
        //connectionList = lineConnectionList;
    }

    //checks if the station exists on this line
    public Boolean existsStation(int id) {

        for (Station station : stationList) {
            if (station.id == id) {
                return true;
            }
        }
        return false;
    }

    public Station returnStation(int id) {
        for (Station station : stationList) {
            if (station.id == id) {
                return station;
            }
        }
        return null;
    }

    private int numStations() {
        return stationList.size();
    }


}
