package com.example.abdsaam.finalmusicplayer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText password;
    private EditText username;
    private Button loginbtn;
    private CardView cardView;
    private TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        mAuth = FirebaseAuth.getInstance();

        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        textView = (TextView) findViewById(R.id.register_here);
        cardView = (CardView) findViewById(R.id.cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("saam") && password.getText().toString().equals("saam")){
                    Intent intent = new Intent(getApplicationContext(),Playlist.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginUser.this, "F LOGGED IN", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MusicLibrary.class);
                startActivity(intent);
            }
        });

    }


}