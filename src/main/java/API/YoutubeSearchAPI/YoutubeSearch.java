package API.YoutubeSearchAPI;

import API.GoogleJobsAPI.GoogleJobs;
import API.GoogleSearchAPI.GoogleSearch;
import API.Request;
import API.SerpAPI;

import java.util.Map;

public class YoutubeSearch extends SerpAPI {
    static final String ENDPOINT = "/search?";

    public YoutubeSearch(){}
    public YoutubeSearch(Map<String, String> urlParameters){
        this.urlParameters=urlParameters;
        this.urlParameters.put("api_key", GoogleSearch.api_key);
        this.urlString+=YoutubeSearch.ENDPOINT;
        this.buildURL();
    }
    public YoutubeSearch(Request request){
        this(request.parameters);
    }

    /**
     * @return JsonObject that stores the query
     */
    public void search(Request request){
        this.urlParameters=request.parameters;
        this.urlParameters.put("api_key", SerpAPI.api_key);
        this.urlString+=YoutubeSearch.ENDPOINT;
        this.buildURL();
    }

}
