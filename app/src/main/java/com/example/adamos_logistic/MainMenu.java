package com.example.adamos_logistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button test = (Button) findViewById(R.id.button);

        test.setOnTouchListener(new OnSwipeTouchListener(MainMenu.this) {

            public void onSwipeLeft() {
                Intent i;
                i = new Intent(MainMenu.this, SwipeMenu.class);
                startActivity(i);
            }
        });
    }
}
