package com.workoutwarrior.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private Button createUserButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        createUserButton1 = (Button) findViewById(R.id.CreateUserButton);
        createUserButton1.setOnClickListener(v->{
            Intent switchCreateUserIntent = new Intent(this,CreateUserActivity.class);
            startActivity(switchCreateUserIntent);
            finish();
        });
    }
}