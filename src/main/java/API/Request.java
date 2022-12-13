package API;

import java.util.HashMap;
import java.util.Map;

public class Request {
    public Map<String, String> parameters = new HashMap<>();

    public Request(String engine){
        this.parameters.put("engine", engine);
    }



}
