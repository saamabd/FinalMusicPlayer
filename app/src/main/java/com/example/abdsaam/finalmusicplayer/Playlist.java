package com.example.abdsaam.finalmusicplayer;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import Data.PlaylistRecycleViewAdapter;
import Data.TopRecyclerViewAdapter;
import Model.PlaylistNames;
import Model.TopTracks;

public class Playlist extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private TopTracks topTracks;
    private List<PlaylistNames> playlistNamesList;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private PlaylistRecycleViewAdapter playlistRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("My Playlists");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showInputDialog();

            }
        });
        playlistNamesList = new ArrayList<>();
        topTracks = (TopTracks) getIntent().getSerializableExtra("toptrack");
        database = FirebaseDatabase.getInstance();



        recyclerView = (RecyclerView) findViewById(R.id.playlistrecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //databaseReference = database.getReference("playlists1");


        //databaseReference.push().setValue(topTracks);
    }

    private void showInputDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.playlistdialog, null);
        final EditText newSearchEdt = (EditText) view.findViewById(R.id.searchEdt);
        Button submitButton = (Button) view.findViewById(R.id.submitButton);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = database.getReference(newSearchEdt.getText().toString());

                    databaseReference.push().setValue(topTracks);
                    topTracks=null;


                dialog.dismiss();
            }

        });
    }
    protected void onStart() {
        super.onStart();


        database.getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PlaylistNames playnames = new PlaylistNames("1",dataSnapshot.getRef().getKey(),"text");
                playlistNamesList.add(playnames);

                playlistRecycleViewAdapter = new PlaylistRecycleViewAdapter(Playlist.this, playlistNamesList);
                recyclerView.setAdapter(playlistRecycleViewAdapter);
                playlistRecycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
