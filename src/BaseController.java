/* Interface for the Controller class
 */

import java.util.ArrayList;

public interface BaseController {
    void init();
    void runProgram(Model model, GUI gui);
    ArrayList<Station> getStationList();


}
