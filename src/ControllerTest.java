import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ControllerTest {

    @Test
    void runProgram() {
        try {
            Model model = new Model();
            Controller controller = new Controller();
            GUI gui = new GUI(controller.getStationList());
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

                // 4. Call the displayOutput method to show the path to user
                gui.displayComponentsInit(pathArray);
            });
        }
        catch (Exception e) {

        }
    }
}