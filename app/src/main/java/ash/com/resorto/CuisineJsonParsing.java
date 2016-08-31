package ash.com.resorto;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsH on 24/10/16.
 */
public class CuisineJsonParsing {
    private List<String> cuisineId = new ArrayList<>();

    public void Parse(JSONObject jsonObject) {

        try {
            JSONArray cuisine_suggestions = jsonObject.getJSONArray("cuisines");
            for (int i = 0; i < cuisine_suggestions.length(); i++) {
                JSONObject ex = cuisine_suggestions.getJSONObject(i);
                JSONObject cuisine = cuisine_suggestions.getJSONObject(i).getJSONObject("cuisine");
                String cuisine_id = cuisine.getString("cuisine_id");
                Log.d("***********************", "PARSED CUISINE ID" + cuisine_id);
                cuisineId.add(cuisine_id);

            }

        } catch (JSONException e) {


        }
    }

    public List<String> getCuisineId() {
        return cuisineId;
    }
}
