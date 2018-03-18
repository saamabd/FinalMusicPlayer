package com.example.abdsaam.finalmusicplayer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.android.volley.Request;
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

import Data.MusicRecycleViewAdapter;
import Data.TopRecyclerViewAdapter;
import Model.ImagesForTracks;
import Model.TopTracks;
import Util.Constants;

public class MusicLibrary extends AppCompatActivity implements SearchView.OnQueryTextListener, TabLayout.OnTabSelectedListener {
    private RecyclerView recyclerView;
    private MusicRecycleViewAdapter musicRecycleViewAdapter;
    private RequestQueue queue;
    private List<TopTracks> topTracksList;
    private TabLayout tabLayout;
    private SearchView searchView;
    private int test=103;
    private String textquery="Drake";
    private int position;
    private String typeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_library);

        queue = Volley.newRequestQueue(this);
        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        searchView.setBackgroundColor(Color.WHITE);
        recyclerView = (RecyclerView) findViewById(R.id.RecycleView1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);

        searchView.setOnQueryTextListener(this);
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.colorAccent));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));

        recyclerView.setLayoutManager(gridLayoutManager);

        topTracksList = new ArrayList<>();
        topTracksList = getTracks1();


        musicRecycleViewAdapter = new MusicRecycleViewAdapter(this, topTracksList);
        recyclerView.setAdapter(musicRecycleViewAdapter);
        musicRecycleViewAdapter.notifyDataSetChanged();


    }
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.menu, menu);


        return true;
    }
    public List<TopTracks> getTracks1(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,Constants.URL + "/v2.2/tracks/top?apikey="
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
                        topTracks.setUrl(trackObject.getString("previewURL"));
                        topTracks.setImg("http://direct.napster.com/imageserver/v2/albums/" + trackObject.getString("albumId") +"/images/150x150.jpg");
                        topTracksList.add(topTracks);
                        musicRecycleViewAdapter.notifyDataSetChanged();
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

    public List<TopTracks> getTracks(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,Constants.URL + "/v2.2/search?apikey="
                + Constants.API_KEY + "&query=" + textquery + "&per_type_limit=" + test, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject toptracksarray = response.getJSONObject("search");
                    JSONObject toptracksarray1 = toptracksarray.getJSONObject("data");
                    JSONArray toptracksarray2 = toptracksarray1.getJSONArray("tracks");
                    for(int i=0; i< toptracksarray2.length(); i++){
                        JSONObject trackObject = toptracksarray2.getJSONObject(i);
                        TopTracks topTracks = new TopTracks();
                        topTracks.setArtistName(trackObject.getString("artistName"));
                        Log.d("test", topTracks.getArtistName());
                        topTracks.setTrack(trackObject.getString("name"));
                        topTracks.setUrl(trackObject.getString("previewURL"));
                        topTracks.setImg("http://direct.napster.com/imageserver/v2/albums/" + trackObject.getString("albumId") +"/images/500x500.jpg");
                        topTracksList.add(topTracks);
                        musicRecycleViewAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onQueryTextSubmit(String s) {

        topTracksList = new ArrayList<>();
        //1=Artist
        if(position==1){

            test=2;
            //2=Albums
        }else if(position==2){

            test=3;
            //3=Tracks
        }else{

            test=4;
        }

        textquery = s;
        topTracksList = getTracks();


        musicRecycleViewAdapter = new MusicRecycleViewAdapter(this, topTracksList);
        recyclerView.setAdapter(musicRecycleViewAdapter);
        musicRecycleViewAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        position = tab.getPosition();
        topTracksList = new ArrayList<>();
        //1=Artist
        if(position==1){

            test=2;
            //2=Albums
        }else if(position==2){

            test=3;
            //3=Tracks
        }else{

            test=4;
        }


        topTracksList = getTracks();


        musicRecycleViewAdapter = new MusicRecycleViewAdapter(this, topTracksList);
        recyclerView.setAdapter(musicRecycleViewAdapter);
        musicRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
