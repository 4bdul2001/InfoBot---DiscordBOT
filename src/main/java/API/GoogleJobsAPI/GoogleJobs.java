package API.GoogleJobsAPI;

import API.GoogleSearchAPI.GoogleSearch;
import API.Request;
import API.SerpAPI;

import java.util.Map;

public class GoogleJobs extends SerpAPI {
    static final String ENDPOINT = "/search?";

    public GoogleJobs(){}
    public GoogleJobs(Map<String, String> urlParameters){
        this.urlParameters=urlParameters;
        this.urlParameters.put("api_key", GoogleSearch.api_key);
        this.urlString+=GoogleJobs.ENDPOINT;
        this.buildURL();
    }
    public GoogleJobs(Request request){
        this(request.parameters);
    }

    /**
     * @return JsonObject that stores the query
     */
    public void search(Request request){
        this.urlParameters=request.parameters;
        this.urlParameters.put("api_key", SerpAPI.api_key);
        this.urlString+=GoogleJobs.ENDPOINT;
        this.buildURL();
    }

}
