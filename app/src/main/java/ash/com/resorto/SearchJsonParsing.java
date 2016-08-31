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
public class SearchJsonParsing {


    private List<String> res_name = new ArrayList<>();
    private List<String> avg_cost = new ArrayList<>();
    private List<String> res_img = new ArrayList<>();
    private List<String> res_address = new ArrayList<>();
    private List<String> res_lattitude = new ArrayList<>();
    private List<String> res_longitude = new ArrayList<>();
    private List<String> res_rating = new ArrayList<>();


    public void Parse(JSONObject jsonObject) {


        try {
            JSONArray restaurants = jsonObject.getJSONArray("restaurants");
            Log.d("***********************", "RESTAURANT LENGTH                   " + restaurants.length());

            for (int i = 0; i < restaurants.length(); i++) {

                JSONObject object = restaurants.getJSONObject(i);
                JSONObject rest = object.getJSONObject("restaurant");
                String name = rest.getString("name");
                int average = rest.getInt("average_cost_for_two");
                String img = rest.getString("thumb");
                JSONObject locn = rest.getJSONObject("location");
                String address = locn.getString("address");
                String lattitude = locn.getString("latitude");
                String longitude = locn.getString("longitude");
                JSONObject rating = rest.getJSONObject("user_rating");
                String user_rating = rating.getString("aggregate_rating");


                res_name.add(name);
                avg_cost.add(String.valueOf(average));
                res_img.add(img);
                res_address.add(address);
                res_lattitude.add(lattitude);
                res_longitude.add(longitude);
                res_rating.add(user_rating);

            }


        } catch (JSONException e) {

        }


    }

    public List<String> getRes_name() {
        return res_name;
    }

    public List<String> getAvg_cost() {
        return avg_cost;
    }

    public List<String> getRes_img() {
        return res_img;
    }

    public List<String> getRes_address() {
        return res_address;
    }

    public List<String> getRes_lattitude() {
        return res_lattitude;
    }

    public List<String> getRes_longitude() {
        return res_longitude;
    }

    public List<String> getRes_rating() {
        return res_rating;
    }
}
