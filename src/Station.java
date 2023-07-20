/* Node class for the multigraph
 */

import java.util.ArrayList;

public class Station extends Node {
    int id;
    String name;
    ArrayList<Line> lines;

    public Station(int stationId, String stationName) {
        id = stationId;
        name = stationName;
        lines = new ArrayList<>();
    }



    @Override
    public String getName() {
        return name;
    }


    public void addLine(Line line) {
        lines.add(line);

    }
    public ArrayList<Line> returnLine() {
        return lines;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }

    // if this station has the line provided
    public boolean hasLine(Line line) {
        for (Line stationLine: this.lines) {
            if (stationLine.name.equals(line.name)) {
                return true;
            }
        }
        return  false;
    }
}
