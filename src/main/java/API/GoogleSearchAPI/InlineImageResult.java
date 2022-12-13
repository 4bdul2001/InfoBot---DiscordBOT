package API.GoogleSearchAPI;

import API.Result;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class InlineImageResult extends Result {

    public InlineImageResult(JsonObject jsonObjectResponse) {
        super(jsonObjectResponse);
    }

    public ArrayList<String> getImageLinks(){
        ArrayList<String> links = new ArrayList<>();
        JsonArray list = this.jsonObjectResponse.getAsJsonArray("inline_images");

        for (int i=0; i<list.size(); i++){
            String imageLink = removeFirstAndLastQuotations(
                    list.get(i).getAsJsonObject().get("thumbnail").toString()
            );
            links.add(imageLink);
        }
        return links;
    }



}
