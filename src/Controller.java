/* Controller class for the program, facilitating the transfer of data
 * between the Model and GUI classes.
 */

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Controller implements BaseController {
    public Model model;
    private GUI gui;


    private ArrayList<Station> stationList;

    private ArrayList<Line> lineList;
    private ArrayList<Connection> connectionList;

    // default constructor
    public Controller() throws IOException, ParseException {
        model = new Model();
        init();
    }

    // constructor for model tests
    public Controller(String resource) throws IOException, ParseException {
        model = new Model(resource);

        // Not necessary
        stationList = model.getStationList();
        lineList = model.getLineList();
        connectionList = model.getConnectionList();
    }

    public void init() {
        stationList = model.getStationList();

        gui = new GUI(model.getStationList());
        runProgram(model, gui);
    }

    public ArrayList<Station> getStationList() {
        return stationList;
    }

    public ArrayList<Line> getLineList() {
        return lineList;
    }

    public ArrayList<Connection> getConnectionList() {return connectionList;}

    @Override()
    public void runProgram(Model model, GUI gui) {
        gui.setVisible(true);


        // 1. Get the submit button to listen actions by user
        gui.getSubmit().addActionListener(e -> {

            // 2. call method returns the stations selected by user
            Station[] travel = gui.retrievesInput();
            ArrayList<Station> path = model.computePath(travel[0], travel[1]);
            Station[] pathArray = new Station[path.size()];

            // 3. cast the ArrayList path to a Station Array to be used by
            // displayComponentsInit method
            for (int i = 0; i < path.size(); i++) {
                pathArray[i] = path.get(i);
            }
            // 4. send the Station and Line pair
            gui.setLines(model.getLinesFromPath());

            // 5. Call the displayOutput method to show the path to user
            gui.displayComponentsInit(pathArray);
        });
    }


}
