package ash.com.resorto;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public static final String TAG = "VolleyPatterns";
    private Geocoder geocoder;
    List<Address> addresses;
    private TextView Locl;
    private RequestQueue requestQueue;
    private static MainActivity instance;
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private String locality;
    private double lat;
    private double lon;
    private int count = 50;
    private int cityId;
    private int entityId;
    private String entityType;
    private int start = 0;
    private double radius = 100;
    private String cuisines;
    private String establishment;
    private String collection;
    private String cat;
    private String sort = "rating";
    private String order = "desc";
    private static final String KEY = "f535bf27fc67a934a369d4b46e7b6238";
    private String CATEGORY_URL;
    private String CUISINE_URL;
    private String ESTABLISHMENT_URL;
    private String COLLECTION_URL;
    private String SEARCH_URL;
    LocationJsonParsing locationJsonParsing;
    CuisineJsonParsing cuisineJsonParsing;
    EstablishmentJsonParsing establishmentJsonParsing;
    CollectionUrlParsing collectionUrlParsing;
    CategoryParsing categoryParsing;
    SearchJsonParsing searchJsonParsing;
    UpdateUrl updateUrl;
    private double latlat;
    private double lonlon;
    private List<String> entityType1 = new ArrayList<>();
    private List<Integer> entityId1 = new ArrayList<>();
    private List<Integer> cityId1 = new ArrayList<>();
    private List<String> cuisineId1 = new ArrayList<>();
    private List<String> establishmentId1 = new ArrayList<>();
    private List<String> collectionId1 = new ArrayList<>();
    private List<String> categoryId1 = new ArrayList<>();
    private List<String> res_name1 = new ArrayList<>();
    private List<String> avg_cost1 = new ArrayList<>();
    private List<String> res_img1 = new ArrayList<>();
    private List<String> res_address1 = new ArrayList<>();
    private List<String> res_lattitude1 = new ArrayList<>();
    private List<String> res_longitude1 = new ArrayList<>();
    private List<String> res_rating1 = new ArrayList<>();
    private StringBuffer cuisineId2 = new StringBuffer();
    private StringBuffer establishmentId2 = new StringBuffer();
    private StringBuffer collectionId2 = new StringBuffer();
    private StringBuffer categoryId2 = new StringBuffer();
    private Button find;
    private RecyclerView recyclerView;

    public List<String> getRes_name1() {
        return res_name1;
    }

    public List<String> getAvg_cost1() {
        return avg_cost1;
    }

    public List<String> getRes_img1() {
        return res_img1;
    }

    public List<String> getRes_address1() {
        return res_address1;
    }

    public List<String> getRes_lattitude1() {
        return res_lattitude1;
    }

    public List<String> getRes_longitude1() {
        return res_longitude1;
    }

    public List<String> getRes_rating1() {
        return res_rating1;
    }

    /**
     * Called when the activity is first created.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        find = (Button) findViewById(R.id.button);
        locationJsonParsing = new LocationJsonParsing();
        cuisineJsonParsing = new CuisineJsonParsing();
        establishmentJsonParsing = new EstablishmentJsonParsing();
        collectionUrlParsing = new CollectionUrlParsing();
        categoryParsing = new CategoryParsing();
        searchJsonParsing = new SearchJsonParsing();
        updateUrl = new UpdateUrl();


        Locl = (TextView) findViewById(R.id.txt);
        Locl.setVisibility(View.INVISIBLE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");
                onLocationChanged(location);
            } else {
                Locl.setText("Failed");
            }
            Log.d("***********************", "not read");
            final String LOCATION_URL = updateUrl.locationUrl(locality, lat, lon, count);
            Log.d("***********************", "read0" + LOCATION_URL);
            firstCall(LOCATION_URL);
            find.setVisibility(View.INVISIBLE);
            find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("locnlat", lat);
                    intent.putExtra("locnlon", lon);
                    startActivity(intent);

                }
            });


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 0);
        }


    }


    public static synchronized MainActivity getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }


    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);

        }
    }


    public void sixthCall(String url) {
        JsonObjectRequest SearchReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("***********************", "SEARCHPARSE                   " + response.toString());
                        searchJsonParsing.Parse(response);

                        avg_cost1 = searchJsonParsing.getAvg_cost();
                        res_address1 = searchJsonParsing.getRes_address();
                        res_img1 = searchJsonParsing.getRes_img();
                        res_lattitude1 = searchJsonParsing.getRes_lattitude();
                        res_longitude1 = searchJsonParsing.getRes_longitude();
                        res_name1 = searchJsonParsing.getRes_name();
                        res_rating1 = searchJsonParsing.getRes_rating();
                        Log.d("***********************", "AVERAGE COST                   " + avg_cost1.size());
                        Log.d("***********************", "RESTAURANT ADDRESS             " + res_address1.size());
                        Log.d("***********************", "RESTAURANT IMAGE               " + res_img1.size());
                        Log.d("***********************", "RESTAURANT LATTITUDE           " + res_lattitude1.size());
                        Log.d("***********************", "RESTAURANT LONGITUDE           " + res_longitude1.size());
                        Log.d("***********************", "RESTAURANT NAME                " + res_name1.size());
                        Log.d("***********************", "RESTAURANT RATING              " + res_rating1.size());
                        find.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("user-key", KEY);
                return headers;
            }
        };
        getInstance().addToRequestQueue(SearchReq);
    }


    public void fifthCall(String url) {
        final JsonObjectRequest CategoryReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        categoryParsing.Parse(response);
                        categoryId1 = categoryParsing.getCategoryId();
                        categoryId2.append(0);
                        for (int i = 1; i < categoryId1.size(); i++) {
                            categoryId2.append("%2C" + categoryId1.get(i));
                        }
                        cat = categoryId2.toString();
                        Log.d("***********************", "CATEGORY" + cat);
                        SEARCH_URL = updateUrl.searchUrl(entityId, entityType, start, count, lat, lon, radius, cuisines, establishment, collection, cat, sort, order);
                        Log.d("***********************", "SEARCH URL" + SEARCH_URL);
                        sixthCall(SEARCH_URL);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("user-key", KEY);
                return headers;
            }
        };
        getInstance().addToRequestQueue(CategoryReq);
    }

    public void fourthCall(String url) {
        final JsonObjectRequest CollectionReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        collectionUrlParsing.Parse(response);
                        collectionId1 = collectionUrlParsing.getCollectionId();
                        collectionId2.append(collectionId1.get(0));
                        for (int i = 1; i < collectionId1.size(); i++) {
                            collectionId2.append("%2C" + collectionId1.get(i));
                        }
                        collection = collectionId2.toString();
                        Log.d("***********************", "COLLECTION" + collection);

                        CATEGORY_URL = updateUrl.categoryUrl();

                        fifthCall(CATEGORY_URL);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("user-key", KEY);
                return headers;
            }
        };
        getInstance().addToRequestQueue(CollectionReq);
    }

    public void thirdCall(String url) {


        final JsonObjectRequest EstablishmentReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        establishmentJsonParsing.Parse(response);
                        establishmentId1 = establishmentJsonParsing.getEstablishmentId();
                        establishmentId2.append(establishmentId1.get(0));
                        for (int i = 1; i < establishmentId1.size(); i++) {
                            establishmentId2.append("%2C" + establishmentId1.get(i));
                        }
                        establishment = establishmentId2.toString();
                        Log.d("***********************", "ESTABLISHMENT" + establishment);
                        COLLECTION_URL = updateUrl.collectionUrl(cityId, lat, lon, count);

                        fourthCall(COLLECTION_URL);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("user-key", KEY);
                return headers;
            }
        };
        getInstance().addToRequestQueue(EstablishmentReq);
    }

    public void secondCall(String url) {

        final JsonObjectRequest CuisineReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        cuisineJsonParsing.Parse(response);
                        cuisineId1 = cuisineJsonParsing.getCuisineId();


                        cuisineId2.append(cuisineId1.get(0));
                        for (int i = 1; i < cuisineId1.size(); i++) {
                            cuisineId2.append("%2C" + cuisineId1.get(i));
                        }
                        cuisines = cuisineId2.toString();
                        Log.d("***********************", "CUISINES" + cuisines);
                        ESTABLISHMENT_URL = updateUrl.establishmentUrl(cityId, lat, lon);

                        thirdCall(ESTABLISHMENT_URL);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("user-key", KEY);
                return headers;
            }
        };
        getInstance().addToRequestQueue(CuisineReq);
    }


    public void firstCall(String url) {
        JsonObjectRequest LocnReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        locationJsonParsing.Parse(response);
                        Log.d("***********************", ""+response);

                        cityId1 = locationJsonParsing.getCityId();
                        entityId1 = locationJsonParsing.getEntityId();
                        entityType1 = locationJsonParsing.getEntityType();
                        cityId = cityId1.get(0);
                        entityId = entityId1.get(0);
                        entityType = entityType1.get(0);
                        CUISINE_URL = updateUrl.cuisineUrl(cityId, lat, lon);

                        secondCall(CUISINE_URL);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("***********************", "ERROR LOCN");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("user-key", KEY);
                return headers;
            }
        };

        getInstance().addToRequestQueue(LocnReq);
    }



    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        } catch (SecurityException e) {
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = (double) (location.getLatitude());
        lon = (double) (location.getLongitude());

        try {
            geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lon, 1);
           locality = addresses.get(0).getLocality();

            Locl.setText(locality);
            Locl.setVisibility(View.VISIBLE);


        } catch (IOException e) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();




        return super.onOptionsItemSelected(item);
    }


}