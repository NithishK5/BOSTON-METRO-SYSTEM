import org.junit.Test;
import static org.junit.Assert.*;



public class GUITest {
    @Test
    public void retrievesInput() {
        try {

            Controller controller = new Controller();
            GUI gui = new GUI(controller.getStationList());
            assertNotNull(gui.retrievesInput());
        } catch (Exception e) {
        }
    }
}
