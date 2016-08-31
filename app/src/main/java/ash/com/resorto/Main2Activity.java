package ash.com.resorto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<String> res_name1 = new ArrayList<>();
    private List<String> avg_cost1 = new ArrayList<>();
    private List<String> res_img1 = new ArrayList<>();
    private List<String> res_address1 = new ArrayList<>();
    private List<String> res_rating1 = new ArrayList<>();
    private List<String> res_lattitude1 = new ArrayList<>();
    private List<String> res_longitude1 = new ArrayList<>();
    private double lattitude;
    private double longitude;
    private double locnlat;
    private double locnlon;
    static Main2Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        instance = this;
        locnlat = getIntent().getDoubleExtra("locnlat", 0);
        locnlon = getIntent().getDoubleExtra("locnlon", 0);
        res_name1 = MainActivity.getInstance().getRes_name1();
        avg_cost1 = MainActivity.getInstance().getAvg_cost1();
        res_img1 = MainActivity.getInstance().getRes_img1();
        res_address1 = MainActivity.getInstance().getRes_address1();
        res_rating1 = MainActivity.getInstance().getRes_rating1();
        res_lattitude1 = MainActivity.getInstance().getRes_lattitude1();
        res_longitude1 = MainActivity.getInstance().getRes_longitude1();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Main2Activity.this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(res_name1, avg_cost1, res_img1, res_address1, res_rating1, res_lattitude1, res_longitude1, Main2Activity.this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public static Main2Activity getInstance() {
        return instance;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void activity() {

        Log.d("***********************", "" + lattitude);
        Log.d("***********************", "" + longitude);
        Intent intent = new Intent(Main2Activity.this, MapsActivity.class);
        intent.putExtra("locnlat", locnlat);
        intent.putExtra("locnlon", locnlon);
        intent.putExtra("lat", lattitude);
        intent.putExtra("lon", longitude);
        startActivity(intent);


    }


}
