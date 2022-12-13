package API.GoogleSearchAPI;

import API.Request;
import API.SerpAPI;

import java.util.Map;

public class GoogleSearch extends SerpAPI {
    static final String ENDPOINT = "/search.json?";
    public GoogleSearch(){}
    public GoogleSearch(Map<String, String> urlParameters){
        this.urlParameters=urlParameters;
        this.urlParameters.put("api_key", GoogleSearch.api_key);
        this.urlString+=GoogleSearch.ENDPOINT;
        this.buildURL();
    }
    public GoogleSearch(Request request){
        this(request.parameters);
    }

    /**
     * @return JsonObject that stores the query
     */
    public void search(Request request){
        this.urlParameters=request.parameters;
        this.urlParameters.put("api_key", SerpAPI.api_key);
        this.urlString+=GoogleSearch.ENDPOINT;
        this.buildURL();
    }

}