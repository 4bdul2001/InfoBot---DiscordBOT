package API.YoutubeSearchAPI;

import API.Result;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class VideoResult extends Result {

    public VideoResult(JsonObject jsonObjectResponse) {
        super(jsonObjectResponse);
    }

    public String getVideoLink(){
        String videoLink = null;
        JsonArray list = this.jsonObjectResponse.getAsJsonArray("video_results");

        videoLink = removeFirstAndLastQuotations(
                list.get(0).getAsJsonObject().get("link").toString()
        );

        return videoLink;
    }

}
