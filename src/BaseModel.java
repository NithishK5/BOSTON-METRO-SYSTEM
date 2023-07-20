/* Interface for the Model class
 */

import java.util.ArrayList;

public interface BaseModel {
    ArrayList<Model.StationLinePair> getLinesFromPath();
    ArrayList<Station> getStationList();
    ArrayList<Line> getLineList();
    Station getStationByName(String name);
    Station getStationByID(int id);
    Boolean connectionExists(Station stationOne, Station stationTwo);




}
