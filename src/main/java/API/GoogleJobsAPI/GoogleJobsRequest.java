package API.GoogleJobsAPI;

import API.Request;

public class GoogleJobsRequest extends Request {
    protected static final String ENGINE = "google_jobs";
    public GoogleJobsRequest(String q) {
        super(GoogleJobsRequest.ENGINE);
        this.parameters.put("q", q);
    }
}
