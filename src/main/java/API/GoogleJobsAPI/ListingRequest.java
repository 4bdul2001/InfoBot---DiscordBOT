package API.GoogleJobsAPI;

public class ListingRequest extends GoogleJobsRequest{

    private final String location = "Toronto, Ontario, Canada";
    private final String hl = "en";

    public ListingRequest(String q) {
        super(q);
        this.parameters.put("location", this.location);
        this.parameters.put("hl", hl);
    }

    public ListingRequest(String q, String page){
        this(q);
        this.parameters.put("start", page);
    }
}
