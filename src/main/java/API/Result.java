package API;

import com.google.gson.JsonObject;

public class Result {

    public JsonObject jsonObjectResponse;

    public Result(JsonObject jsonObjectResponse){
        this.jsonObjectResponse=jsonObjectResponse;
    }

    /**
     * @param phrase is any texts that requires outer quotations removed
     * @return "text" -> text
     */
    public String removeFirstAndLastQuotations(String phrase) {
        return phrase.substring(1, phrase.length()-1);
    }

}
