package ash.com.resorto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsH on 24/10/16.
 */
public class CategoryParsing {


    private List<String> categoryId = new ArrayList<>();

    public void Parse(JSONObject jsonObject) {

        try {
            JSONArray category_suggestions = jsonObject.getJSONArray("categories");
            for (int i = 0; i < category_suggestions.length(); i++) {
                JSONObject ex = category_suggestions.getJSONObject(i);
                JSONObject category = category_suggestions.getJSONObject(i).getJSONObject("categories");
                int category_id = category.getInt("id");
                categoryId.add(String.valueOf(category_id));

            }

        } catch (JSONException e) {


        }
    }

    public List<String> getCategoryId() {
        return categoryId;
    }

}
