import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelTest {
    Controller controller;

    public ModelTest() throws IOException, ParseException {
        String resource = "res/test.json";
        controller = new Controller(resource);

        stationBuilderTest();
        connectionBuilderTest();
        lineBuilderTest();
        pathFindingTest();

    }

    // Not necessary
    @Test
    public void stationBuilderTest() {
        Station stationA = new Station(1, "Station A");
        Station stationB = new Station(2, "Station B");
        Station stationC = new Station(3, "Station C");
        Station stationD = new Station(5, "Station E");

        // check if the station objects have been created correctly

        assertEquals(controller.getStationList().get(1).toString(), stationA.toString());
        assertEquals(controller.getStationList().get(2).toString(), stationB.toString());
        assertEquals(controller.getStationList().get(3).toString(), stationC.toString());
        assertEquals(controller.getStationList().get(5).toString(), stationD.toString());

        // check the number of stations
        assertEquals(controller.getStationList().size(), 6);

    }

    // Not necessary
    @Test
    public void connectionBuilderTest() {
        // check if connections exist
        ArrayList<Station> stations = controller.getStationList();

        assertTrue(controller.model.connectionExists(stations.get(1), stations.get(3)));
        assertTrue(controller.model.connectionExists(stations.get(3), stations.get(2)));
        assertTrue(controller.model.connectionExists(stations.get(4), stations.get(3)));
        assertTrue(controller.model.connectionExists(stations.get(3), stations.get(5)));

        assertTrue(controller.model.connectionExists(stations.get(3), stations.get(1)));
        assertTrue(controller.model.connectionExists(stations.get(2), stations.get(3)));
        assertTrue(controller.model.connectionExists(stations.get(3), stations.get(4)));
        assertTrue(controller.model.connectionExists(stations.get(5), stations.get(3)));




        assertEquals(controller.getConnectionList().size(), 16);
    }

    @Test
    public void lineBuilderTest() {
        assertEquals(controller.getLineList().size(), 2);
        ArrayList<Line> lines = controller.getLineList();
        ArrayList<Station> stations = controller.getStationList();

        ArrayList<Station> white = new ArrayList<>();
        white.add(stations.get(1));
        white.add(stations.get(2));
        white.add(stations.get(3));
        white.add(stations.get(0));

        ArrayList<Station> black = new ArrayList<>();
        black.add(stations.get(3));
        black.add(stations.get(4));
        black.add(stations.get(5));
        black.add(stations.get(0));

        assertEquals(lines.get(0).stationList, white);
        assertEquals(lines.get(1).stationList, black);


    }
    @Test
    public void pathFindingTest() {

    }

    @Test
    public void lineFindingTest() {
        ArrayList<Station> stations = controller.getStationList();
        ArrayList<Line> lines = controller.getLineList();
        controller.model.path.add(stations.get(1));
        controller.model.path.add(stations.get(3));
        controller.model.path.add(stations.get(4));
        ArrayList<Model.StationLinePair> pairs = controller.model.getLinesFromPath();
        assertEquals(pairs.get(0).getStation(), stations.get(1));
        assertEquals(pairs.get(0).getLine(), lines.get(0));

        assertEquals(pairs.get(1).getStation(), stations.get(3));
        assertEquals(pairs.get(1).getLine(), lines.get(0));

        assertEquals(pairs.get(2).getStation(), stations.get(4));
        assertEquals(pairs.get(2).getLine(), lines.get(1));





    }

}
