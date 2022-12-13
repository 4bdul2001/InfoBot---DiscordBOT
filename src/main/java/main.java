import API.GoogleJobsAPI.GoogleJobs;
import API.GoogleJobsAPI.ListingRequest;
import API.GoogleJobsAPI.ListingResult;

import java.io.UnsupportedEncodingException;

public class main {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        Map<String, String> parameter = new HashMap<>();
//
//        parameter.put("q", "Apple");
//        parameter.put("engine", "google");
//
//        GoogleSearch search = new GoogleSearch(parameter);
//        JsonObject obj = search.getJson();
//        int size = ((JsonArray) obj.get("inline_images")).size();
//        String test = obj.get("inline_images").toString();
//
//        JsonArray list = obj.getAsJsonArray("inline_images");
//        JsonObject temp = list.get(0).getAsJsonObject();
//
//        System.out.println(test);
//        System.out.println(list.size());
//        System.out.println(temp.get("link"));

        GoogleJobs googleJobs = new GoogleJobs();
        ListingRequest request = new ListingRequest("Software Developer", "1");
        googleJobs.search(request);
        ListingResult result = new ListingResult(googleJobs.getJson());
        result.getJobs();

    }

}
