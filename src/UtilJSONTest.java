import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import static org.junit.Assert.*;
public class UtilJSONTest {

    @Test
    public void read() {
        UtilJSON json = new UtilJSON();
        Assertions.assertThrows(ParseException.class, () -> {
            json.read("res/bostonmetro.json");
        });
    }

    @Test
    public void read2() {
        UtilJSON json = new UtilJSON();
        Assertions.assertThrows(IOException.class, () -> {
            json.read("res/bostonmetro.json");
        });

    }


}