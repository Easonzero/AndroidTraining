package com.eason.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        Log.d("test","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("test","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("test","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("test","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("test","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("test","onDestroy");
    }
}
