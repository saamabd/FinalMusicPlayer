package com.example.abdsaam.finalmusicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.Window;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Data.TopRecyclerViewAdapter;
import Model.ImagesForTracks;
import Model.TopTracks;
import Util.Constants;

import static com.android.volley.Request.*;

public class TopPlay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TopRecyclerViewAdapter topRecyclerViewAdapter;
    private List<TopTracks> topTracksList;
    private List<ImagesForTracks> imagesUrls;
    private List<String> images;
    private RequestQueue queue;
    private String testimg;

    public String test = "http://static.rhap.com/img/70x70/9/0/5/4/10284509_70x70.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_top_play);



         queue = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.testrun);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(gridLayoutManager.VERTICAL);


        recyclerView.setLayoutManager(gridLayoutManager);


        topTracksList = new ArrayList<>();
        topTracksList = getTracks();


        topRecyclerViewAdapter = new TopRecyclerViewAdapter(this, topTracksList);
        recyclerView.setAdapter(topRecyclerViewAdapter);
        topRecyclerViewAdapter.notifyDataSetChanged();
    }

    public List<TopTracks> getTracks(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET,Constants.URL + "/v2.2/tracks/top?apikey="
                + Constants.API_KEY + "&limit=103", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray toptracksarray = response.getJSONArray("tracks");

                    for(int i=0; i< toptracksarray.length(); i++){
                        JSONObject trackObject = toptracksarray.getJSONObject(i);
                        TopTracks topTracks = new TopTracks();
                        topTracks.setArtistName(trackObject.getString("artistName"));
                        topTracks.setTrack(trackObject.getString("name"));
                        topTracks.setImg("http://direct.napster.com/imageserver/v2/albums/" + trackObject.getString("albumId") +"/images/150x150.jpg");
                        topTracksList.add(topTracks);
                        topRecyclerViewAdapter.notifyDataSetChanged();
                    }


                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

        return topTracksList;
    }

}
