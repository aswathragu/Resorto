package ash.com.resorto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsH on 24/10/16.
 */
public class CollectionUrlParsing {

    private List<String> collectionId = new ArrayList<>();
    public void Parse(JSONObject jsonObject) {

        try {
            JSONArray collection_suggestions = jsonObject.getJSONArray("collections");
            for(int i = 0;i<collection_suggestions.length();i++){
                JSONObject ex = collection_suggestions.getJSONObject(i);
                JSONObject collection = collection_suggestions.getJSONObject(i).getJSONObject("collection");
                int collection_id = collection.getInt("collection_id");
                collectionId.add(String.valueOf(collection_id));
            }

        } catch (JSONException e) {


        }
    }

    public List<String> getCollectionId() {
        return collectionId;
    }






}
