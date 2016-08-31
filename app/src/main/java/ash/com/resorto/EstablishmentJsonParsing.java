package ash.com.resorto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsH on 24/10/16.
 */
public class EstablishmentJsonParsing {

    private List<String> establishmentId = new ArrayList<>();
    public void Parse(JSONObject jsonObject) {

        try {
            JSONArray establishment_suggestions = jsonObject.getJSONArray("establishments");
            for(int i = 0;i<establishment_suggestions.length();i++){
                JSONObject ex = establishment_suggestions.getJSONObject(i);
                JSONObject establishment = establishment_suggestions.getJSONObject(i).getJSONObject("establishment");
                int establishment_id = establishment.getInt("id");
                establishmentId.add(String.valueOf(establishment_id));
            }

        } catch (JSONException e) {


        }
    }

    public List<String> getEstablishmentId() {
        return establishmentId;
    }


}
