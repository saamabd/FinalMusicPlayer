package com.example.abdsaam.finalmusicplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
//import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vansuita.gaussianblur.GaussianBlur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import Model.ImagesForTracks;
import Model.TopTracks;
import jp.wasabeef.blurry.Blurry;
//import jp.wasabeef.glide.transformations.BlurTransformation;

//import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer;
    private TextView startTxt, endStart;
    private SeekBar seekBar;
    private ImageButton preButton, playButton, NextButton;
    private Thread thread;
    private TopTracks topTracks;
    private RequestQueue queue;
    private ImageView imageView;
    private String url;
    private TextView musicname,artistname_player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_music_player);
        topTracks = (TopTracks) getIntent().getSerializableExtra("toptrack");
        url = topTracks.getUrl();
        setUpUI();
        imageView = (ImageView) findViewById(R.id.imageView);
        //ImageView mContainerView = (ImageView) findViewById(R.id.backgroundimage1);
        musicname = (TextView) findViewById(R.id.musicname2);
        artistname_player = (TextView) findViewById(R.id.artistname_player);
        musicname.setText(topTracks.getTrack());
        artistname_player.setText(topTracks.getArtistName());
        Picasso.with(this).load(topTracks.getImg()).placeholder(android.R.drawable.ic_btn_speak_now).into(imageView);
        //Picasso.with(this).load(topTracks.getImg()).placeholder(android.R.drawable.ic_btn_speak_now).into(mContainerView);


       // Glide.with(this).load(R.drawable.postmalone)
              //  .apply(bitmapTransform(new BlurTransformation(25)))
               // .into((ImageView) findViewById(R.id.backgroundimage1));
      // Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.postmalone);
      //  Bitmap blurredBitmap = BlurBuilder.blur( this, originalBitmap );
       //mContainerView.setBackground(new BitmapDrawable(getResources(), blurredBitmap));
     //   GaussianBlur.with(this).size(300).radius(10).put(R.id.backgroundimage1, imageView);


        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                int currentpos = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();

                startTxt.setText(dateFormat.format(new Date(currentpos)));
                endStart.setText(dateFormat.format(new Date(duration-currentpos)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    public void setUpUI() {
       // url = "http://listen.vo.llnwd.net/g3/5/7/6/3/1/1387013675.mp3";
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rockstar);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        startTxt = (TextView) findViewById(R.id.leftime);
        endStart = (TextView) findViewById(R.id.righttime);

        preButton =(ImageButton) findViewById(R.id.backbtn);

        playButton =(ImageButton) findViewById(R.id.playbtn);
        NextButton =(ImageButton) findViewById(R.id.forwardbtn);

        preButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        NextButton.setOnClickListener(this);

    }


    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backbtn:{

            }
            break;
            case R.id.playbtn:{
                if(mediaPlayer.isPlaying()){
                    pauseMusic();
                }else{
                    startMusic();
                }
            }
            break;
            case R.id.forwardbtn:{

            }
            break;
        }
    }

    public void pauseMusic(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            playButton.setBackgroundResource(R.drawable.play);
        }
    }
    public void startMusic(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            updateThread();
            playButton.setBackgroundResource(R.drawable.pause);
        }
    }

    public void updateThread(){
        thread = new Thread(){
            @Override

            public void run() {

                try{
                    while(mediaPlayer != null && mediaPlayer.isPlaying()){

                        thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void run() {
                                int newPosition = mediaPlayer.getCurrentPosition();
                                int maxPosition = mediaPlayer.getDuration();
                                seekBar.setMax(maxPosition);
                                seekBar.setProgress(newPosition);

                                //updatetext

                                startTxt.setText(String.valueOf(new SimpleDateFormat("mm:ss")
                                        .format(new Date(mediaPlayer.getCurrentPosition()))));

                                endStart.setText(String.valueOf(new SimpleDateFormat("mm:ss")
                                        .format(new Date(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()))));

                            }
                        });
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }


        };
        thread.start();
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer !=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
           // mediaPlayer = null;
            thread.interrupt();
        }
       // thread.interrupt();
        thread = null;

        super.onDestroy();
    }
}
