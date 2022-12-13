package API.GoogleSearchAPI;

import API.Request;

public class GoogleSearchRequest extends Request {
    protected static final String ENGINE = "google";
    public GoogleSearchRequest(String q) {
        super(GoogleSearchRequest.ENGINE);
        this.parameters.put("q", q);
    }
}
