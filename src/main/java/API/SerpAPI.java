package API;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public abstract class SerpAPI {
    public static final String api_key = "25b7e0ed0a18b9d99032c1daba4c92a202abf13503e6781114f4412a8b7ec41a";
    protected String urlString;
    public static final String AMPERSAND = "&";
    protected Map<String, String> urlParameters;

    public SerpAPI(){
        this.urlString="https://serpapi.com";
    }

    /**
     * @return class fields
     */
    public Map<String, String> getUrlParameters(){
        return this.urlParameters;
    }
    public void setUrlParameters(Map<String, String> urlParameters){
        this.urlParameters=urlParameters;
    }
    public String getUrlString(){
        return this.urlString;
    }

    /*
            Simply builder the URL for the search query -> buildURL() & formatParameter()
    */
    public void buildURL(){
        boolean firstEntry=true;
        for (Map.Entry<String,String> entry: this.urlParameters.entrySet()){
            if (firstEntry){
                this.urlString += entry.getKey() + "=" +
                        this.formatParameter(entry.getValue());
                firstEntry=false;
            } else {
                this.urlString += this.AMPERSAND + entry.getKey() + "=" +
                        this.formatParameter(entry.getValue());
            }
        }
    }
    public String formatParameter(String parameter){
        return parameter.replaceAll(" ", "+");
    }

    public JsonObject getJson(){
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        JsonObject obj;
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(this.urlString))
                    .GET()
                    .build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            obj = gson.fromJson(getResponse.body(), JsonObject.class);
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

}
