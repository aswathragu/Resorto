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
public class LocationJsonParsing {

    private List<String> entityType = new ArrayList<>();
    private List<Integer> entityId = new ArrayList<>();
    private List<Integer> cityId = new ArrayList<>();

    public void Parse(JSONObject jsonObject) {

        try {
            JSONArray location_suggestions = jsonObject.getJSONArray("location_suggestions");
            for(int i = 0;i<location_suggestions.length();i++){
                JSONObject ex = location_suggestions.getJSONObject(i);
                int entity_id = ex.getInt("entity_id");
                String entity_type = ex.getString("entity_type");
                int city_id = ex.getInt("city_id");

                entityType.add(entity_type);
                entityId.add(entity_id);
                cityId.add(city_id);

            }

        } catch (JSONException e) {



        }
    }

    public List<String> getEntityType() {
        return entityType;
    }

    public List<Integer> getEntityId() {
        return entityId;
    }

    public List<Integer> getCityId() {
        return cityId;
    }
}
