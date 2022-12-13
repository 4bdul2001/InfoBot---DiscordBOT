package API.YoutubeSearchAPI;

public class VideoRequest extends YoutubeSearchRequest{

    private String search_query;

    public VideoRequest(String search_query) {
        super();
        this.search_query = search_query;
        this.parameters.put("search_query", this.search_query);
    }
}
