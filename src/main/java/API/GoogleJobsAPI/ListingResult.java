package API.GoogleJobsAPI;

import API.Result;
import FileReport.TextFileBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;

public class ListingResult extends Result {

    private static final String fileName = "jobListing";
    public static final String PATH = "jobListing.txt";

    public ListingResult(JsonObject jsonObjectResponse) {
        super(jsonObjectResponse);
    }

    public void getJobs(){
        TextFileBuilder builder = TextFileBuilder.getInstance();
        File file = builder.getFile(ListingResult.fileName);
        builder.writeToFile(file, this.jobQueryOutput());
    }

    public String jobQueryOutput(){
        StringBuilder output = new StringBuilder();
        JsonArray list = this.jsonObjectResponse.getAsJsonArray("jobs_results");
        JsonObject tempObj;
        for(int i=0; i<list.size(); i++){
            output.append("**Job #" + i + "**\n");
            tempObj = list.get(i).getAsJsonObject();
            output.append("Title: " + tempObj.get("title").toString() + "\n");
            output.append("Company: " + tempObj.get("company_name").toString() + "\n");

            String description = this.removeFirstAndLastQuotations(
                    tempObj.get("description").toString()
            );
            output.append("Description: " + description + "\n");
            output.append("Location: " + tempObj.get("location").toString() + "\n");

            JsonArray temp = tempObj.get("extensions").getAsJsonArray();
            if(temp!=null){
                boolean appended = false;
                while (!appended){
                    if (temp.size()>=1){output.append("Posted: " + temp.get(0) + "\n");}
                    if (temp.size()>=2){output.append("Schedule Type: " + temp.get(1) + "\n");}
                    if (temp.size()>=3){output.append("Degree Required: " + temp.get(2) + "\n");}
                    appended=true;
                }
            }
            output.append("\n");
        }
        return output.toString();
    }



}
