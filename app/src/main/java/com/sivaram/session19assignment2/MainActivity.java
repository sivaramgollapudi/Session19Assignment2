package com.sivaram.session19assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.sivaram.session19assignment2.network.CallWebService;
import com.sivaram.session19assignment2.network.NetworkStatus;
import com.sivaram.session19assignment2.network.OnWebServiceResult;
import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnWebServiceResult {

    // URL to get upcoming tv shows
    private static String url = "http://api.themoviedb.org/3/tv/top_rated?api_key=8496be0b2149805afa458ab8ec27560c";

    // Create ArrayList object for MovieDetails Class
    List<MovieDetails> model= new ArrayList<>();

    // Create Object of ListView.
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set List View Reference.
        list= (ListView)findViewById(R.id.list);
        getMovies();
    }

    private void getMovies(){
        // Create Object of FormEncodingBuilder.
        FormEncodingBuilder parameters= new FormEncodingBuilder();

        // Set Parameters
        parameters.add("id", "123456");
        parameters.add( "action", "get_contacts");

        // Check Whether Device Connected to Internet
        if(NetworkStatus.getInstance(this).isOnline(this)){
            CallWebService call = new CallWebService(this,url,parameters, CommonUtilities.SERVICE_TYPE.GET_DATA,this);
            // Show Progress Bar
            CommonUtilities.showLoading(this, "Please wait...", false);
            call.execute();
        }else{
            // Show Error Message if no network connection available
            Toast.makeText(this, "Please check your internet connection!!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getWebResponse(String result, CommonUtilities.SERVICE_TYPE type) {
        CommonUtilities.hideLoading();
        try {
            // Create JSon OBject from result object.
            JSONObject obj= new JSONObject(result);

            // Read Json Object from Json Array of index 0
            JSONArray arr= obj.getJSONArray("results");

            // Loop Through Result Array
            for(int i=0; i<arr.length(); i++){
                // Get Json OBject of the Array
                JSONObject jobj= arr.getJSONObject(i);

                // Create Object of Movie Details
                MovieDetails handler= new MovieDetails();

                // Set ID
                handler.setId(jobj.getInt("id"));

                // Set Movie Name
                handler.setMovieName(jobj.getString("name"));

                // Set Movie Vote Count
                handler.setVoteCount(jobj.getInt("vote_count"));
                model.add(handler);
            }

            // Crate CustomList Adapter Object and set to List.
            CustomListAdapter adapter= new CustomListAdapter(this, model);
            list.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
