/* GUI class for implementing swing GUI elements and gathering
 * data from the user.
 */

/* Importing Swing components.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame implements BaseGUI {

    /** Initialising Swing components.
     *
     * @param location The station 'from' in 'to and from'.
     * @param destination The station 'to' in 'to and from'.
     * @param submit The button to submit the current stations.
     * @param al1 THe event listener for 'submit'.
     */
    private JComboBox<Station> location;
    private JComboBox<Station> destination;
    private JButton submit;
    private ActionListener al1;
    private JTable table;
    private JFrame display;

    private ArrayList<Model.StationLinePair> lines;

    /** Initialising output array.
     *
     * @param stations The array of the two selected stations.
     * @param stationList The list of station object passed from the controller
     */
    private Station[] stations;
    private ArrayList<Station> stationList;

    /** Constructor split into parts for ease of understanding.
     */
    public GUI(ArrayList<Station> stationList) {
        init();
        listenersInit();
        this.stationList = stationList;
        componentsInit(stationList);
        layoutInit(location, destination, submit);
        displayComponentsInit(stations);
    }

    /** Frame constructor for the window, sets basic settings.
     */
    private void init() {
        setTitle("Boston Metro Map");
        setPreferredSize(new Dimension(600, 500));
        setMaximumSize(new Dimension(600, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        display = new JFrame("Route");
    }

    /** Constructor for event listeners; buttons press updates
     * output array.
     * TODO Add listeners for JComboBoxes so that they cannot
     *   have the same value when submitting.
     */
    private void listenersInit() {
        al1 = e -> {
            sendStations(location, destination, stations);
            System.out.println("Stations: ");
            for (Station s: stations) {
                System.out.println(s.name);
            }
            System.out.println(" ");
            displayComponentsInitFinal(lines);
        };
    }

    /** Constructor for Swing components.
     */
    private void componentsInit(ArrayList<Station> sl) {
        stationList = sl;
        stations = new Station[2];

        location = new JComboBox<>();
        location.setRenderer(new listCellRenderer());

        destination = new JComboBox<>();
        destination.setRenderer(new listCellRenderer());

        for (Station s: stationList) {
            if (s.name == null) {
                continue;
            }
            location.addItem(s);
            destination.addItem(s);
        }

        submit = new JButton("Submit");
        submit.addActionListener(al1);
    }

    /** Constructor for component layout settings.
     */
    private void layoutInit(JComboBox<Station> location, JComboBox<Station> destination, JButton submit) {
        var contentPane = getContentPane();
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(location);
        panel.add(destination);
        panel.add(submit);
        contentPane.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    /** Constructor for Swing components.
     */

    public void displayComponentsInit(Station[] stations) {
        // Replace station with path from Model
        if (stations[0] != null) {
            String[][] path = new String[stations.length][2];
            for (int i = 0; i < stations.length; i++) {
                // Replace 'Red' with the colour of the station

                //TODO find line corresponding to station
                path[i] = new String[]{stations[i].name, lines.get(i).getLine().name};
            }
            table = new JTable(path, new String[]{"Route", "Line"});
            displayInit(display, table);
        }
    }



    public void displayComponentsInitFinal(ArrayList<Model.StationLinePair> slp) {

        for (Model.StationLinePair s: slp) {
            System.out.println(s.getStation().name);
        }

        String[][] path = new String[slp.size()][2];
        for (int i = 0; i < slp.size(); i++) {
            path[i] = new String[]{slp.get(i).getStation().name, slp.get(i).getLine().name};
        }
        table = new JTable(path, new String[]{"Route", "Line"});
        displayInit(display, table);
    }

    /** Constructor for component layout in the display window.
     */
    private void displayInit(JFrame display, JTable table) {
        display.setSize(new Dimension(600, 500));
        display.setLocation(this.getX() + this.getWidth(), this.getY());

        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.add(table);
        display.add(panel);

        display.setVisible(true);
    }

    /** Function to update the value of the output array
     * with the currently selected stations.
     */
    private void sendStations(JComboBox<Station> location, JComboBox<Station> destination, Station[] stations) {
        stations[0] = (Station) location.getSelectedItem();
        stations[1] = (Station) destination.getSelectedItem();
    }


    /** Renderer for the JComboBoxes so that they display
     * the name of each station in the box
     */
    public static class listCellRenderer extends DefaultListCellRenderer {

        public Component getListCellRendererComponent(
                JList list,
                Object v,
                int i,
                boolean isActive,
                boolean inFocus) {
            if (v instanceof Station) {
                v = ((Station)v).name;
            }
            super.getListCellRendererComponent(list, v, i, isActive, inFocus);
            return this;
        }


    }

    // Getter method for submit button
    public JButton getSubmit() {
        return this.submit;
    }

    public Station[] retrievesInput() {
        sendStations(location, destination, stations);
        return this.stations;
    }

    public void setLines(ArrayList<Model.StationLinePair> l) {

        lines = l;
    }

}
