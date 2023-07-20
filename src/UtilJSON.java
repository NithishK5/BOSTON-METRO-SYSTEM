import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class UtilJSON {

    public UtilJSON() {
    }

    public JSONArray read(String resource) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(resource));

        return (JSONArray) obj;

    }







}
