/* Model class for calculating the optimal route by using
 * the information sent from the GUI to path a multigraph
 */


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Model implements BaseModel {
    private ArrayList<Line> lineList = new ArrayList<>();
    private ArrayList<Station> stationList = new ArrayList<>();
    private ArrayList<Connection> connectionList = new ArrayList<>();
    public ArrayList<Station> path = new ArrayList<>();

    private MultiGraphADT<Station, Connection> multiGraphADT;




    // default constructor for default resource file
    public Model() throws IOException, ParseException {
        String RESOURCE_FILE = "res/bostonmetro.json";
        initData(RESOURCE_FILE);
        multiGraphADT = new MultiGraphADT<>(stationList, connectionList);
    }


    // constructor for test resource file
    public Model(String resource) throws IOException, ParseException {
        initData(resource);
        multiGraphADT = new MultiGraphADT<>(stationList, connectionList);
    }







    // imports data from the JSON and creates Line, Station, and Connection objects.
    private void initData(String resource) throws IOException, ParseException {
        //add station 0 for line termination
        Station zero = new Station(0, null);
        stationList.add(zero);


        UtilJSON utilJSON = new UtilJSON();
        JSONArray stations = utilJSON.read(resource);

        stationBuilder(stations);


        //add terminating station to each line
        for (Line l : lineList) {
            l.stationList.add(zero);
        }

        //System.out.println(stationList.toString());

        for (Object station : stations) {
            JSONObject stationObject = (JSONObject) station;
            JSONArray lineArray = (JSONArray) stationObject.get("lines");
            //JSONObject lineObject = (JSONObject) lineArray.get(0);


            //get the predecessor and successor
            Long currentID = (Long) stationObject.get("id");


            // go through each line going through the station
            for (Object lineObjectObject : lineArray) {
                JSONObject lineObject = (JSONObject) lineObjectObject;
                String lineName = (String) lineObject.get("name");
                Long predecessorID = (Long) lineObject.get("predecessorStation");
                Long successorID = (Long) lineObject.get("successorStation");



                //check if the line exists
                if (existsLine(lineName)) {
                    Line line = returnLine(lineName);
                    if (line != null) {
                        // get the predecessor, current, and successor Station objects
                        Station predecessor = getStationByID(predecessorID.intValue());
                        Station current = getStationByID(currentID.intValue());
                        Station successor = getStationByID(successorID.intValue());

                        //create a connection on the given line between given stations
                        constructConnection(line, predecessor, current);
                        constructConnection(line, current, predecessor);
                        constructConnection(line, current, successor);
                        constructConnection(line, successor, current);



                    }
                }
            }
        }

        /*
        for (Connection connection : connectionList) {
            if (connection.stationOne != null && connection.stationTwo!=null) {
            System.out.println(connection.id + ". " + connection.stationOne.toString() + " - " + connection.stationTwo.toString() );
            } else {
                System.out.println(connection.id + ". " + connection.stationOne + " - " + connection.stationTwo + " BROKEN-------------------------------------");
            }
        }
          */
    }

    private void constructConnection(Line line, Station stationOne, Station stationTwo) {
        Station nullStation = getStationByID(0);
        if (!stationOne.equals(nullStation) && !stationTwo.equals(nullStation) && !connectionExists(stationOne, stationTwo)) {
            Connection connection = new Connection(connectionList.size() + 1, stationOne, stationTwo);
            connectionList.add(connection);
            line.connectionList.add(connection);
        }


    }


    private void stationBuilder(JSONArray stations) {
        //go through each station in the JSON array and create the lines and stations
        for (Object station : stations) {
            JSONObject stationObject = (JSONObject) station;

            // add the line if it doesn't exist already
            JSONArray lineArray = (JSONArray) stationObject.get("lines");


            //create new station and add it to the station list
            Long stationID = (Long) stationObject.get("id");
            Station newStation = new Station(stationID.intValue(), (String) stationObject.get("name"));
            stationList.add(newStation);

            for (Object l : lineArray) {
                JSONObject lineObject = (JSONObject) l;
                String lineName = (String) lineObject.get("name");

                Line line;

                //add the line if it doesn't exist already
                if (!existsLine(lineName)) {
                    line = new Line(lineList.size() + 1, lineName);
                    lineList.add(line);
                } else {
                    line = returnLine(lineName);
                }

                //add the station to its line

                newStation.addLine(line);
                if (line != null) {
                    line.stationList.add(newStation);
                }
            }

        }
    }


    public ArrayList<Station> computePath(Station start, Station destination) {
        path = multiGraphADT.breadthFirstSearch(start, destination);
        return path;
    }


    // method to return the lines to use for each station
    @Override
    public ArrayList<StationLinePair> getLinesFromPath() {
        //ArrayList holding the Stations and their Lines
        ArrayList<StationLinePair> stationLine = new ArrayList<>();

        //Hashmap to store most common lines for the path
        Map<Line, Integer> lineCount = new HashMap<>();
        for (Station station: path) {
            for (Line line: station.lines) {
                lineCount.merge(line, 1, Integer::sum);
            }
        }

        /*
        for (Map.Entry<Line, Integer> entry : lineCount.entrySet()) {
            Line line = entry.getKey();
            Integer num = entry.getValue();
            System.out.println(line.name + " : " + num);
        }
        */

        // turn Hashmap<Line, Integer> into ArrayList<Line> based on occurrence of line
        final List<Line> sortedLines = lineCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                map(Map.Entry::getKey)
                .collect(Collectors.toList());

        /*
        System.out.println("Printing the lines:");
        for (Line line: sortedLines) {
            System.out.println(line.name);
        }
        System.out.println("");




        System.out.println("Printing the stations:");
        for (Station station: path) {
            System.out.println(station.name);
        }
        System.out.println("");

        */
        // Create Hashmap of the stations and corresponding lines
        for (Station station: path) {
            for (Line line: sortedLines) {
                if (station.lines.contains(line)) {
                    StationLinePair pair = new StationLinePair();
                    pair.line = line;
                    pair.station = station;
                    stationLine.add(pair);
                    break;
                }
            }
        }

        /*
        System.out.println("Printing the station and corresponding line: ");
        for (StationLinePair pair: stationLine) {
            Station station = pair.station;
            Line line = pair.line;
            System.out.println(station.name + " : " + line.name);
        }
        System.out.println(stationLine);

         */
        return stationLine;
    }

    public ArrayList<Station> getStationList() {
        return stationList;
    }

    public ArrayList<Connection> getConnectionList() {
        return connectionList;
    }

    public ArrayList<Line> getLineList() {
        return lineList;
    }
    public Station getStationByName(String name) {
        for (Station station: stationList) {
            if (station.name != null && station.name.equals(name)) {
                return station;
            }
        }
        return null;
    }

    @Override
    public Station getStationByID(int id) {
        for (Station station: stationList) {
            if (station.id == id) {
                return station;
            }
        }
        return null;
    }

    //create a connection on the given line between given stations
    @Override
    public Boolean connectionExists(Station stationOne, Station stationTwo) {
        for (Connection connection: connectionList) {
            //System.out.println("Checking: " + connection.stationOne + " - " + connection.stationTwo);
            if (connection.stationOne != null && connection.stationTwo != null && connection.stationOne == stationOne && connection.stationTwo == stationTwo) {
                //System.out.println("Duplicate!");
                return true;
            }
        }
        return false;
    }

    private Boolean existsLine(String name) {
        for (Line line : lineList) {
            if (line.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private Line returnLine(String name) {
        for (Line line : lineList) {
            if (line.name.equals(name)) {
                return line;
            }
        }
        return null;
    }

    public static class StationLinePair {
        private Station station;
        private Line line;

        public Station getStation() {
            return station;
        }

        public Line getLine() {
            return line;
        }
    }
}
