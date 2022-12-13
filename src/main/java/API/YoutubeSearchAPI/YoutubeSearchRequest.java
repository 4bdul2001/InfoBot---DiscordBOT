package API.YoutubeSearchAPI;

import API.Request;

public class YoutubeSearchRequest extends Request {
    private static final String ENGINE = "youtube";
    public YoutubeSearchRequest() {
        super(YoutubeSearchRequest.ENGINE);
    }
}
