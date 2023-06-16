package com.example.myapplication.presentation.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    public static final String KEY_ALREADY_LOGGED_IN = "alreadyLoggedIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash_screen);

        try {
            checkAndCreateFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
        Boolean alreadyLoggedIn = sharedPreferences.getBoolean(StartActivity.KEY_ALREADY_LOGGED_IN,false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!alreadyLoggedIn){
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else{
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },2300);
    }

    private void checkAndCreateFile() throws IOException {
        File file = new File(getFilesDir(), "nalozi.txt");
        if (!file.exists()) {
//            file.createNewFile();
            FileOutputStream fout = openFileOutput("nalozi.txt", MODE_PRIVATE);

            String nalog1 = "nalog1@gmail.com ,nalog1 ,Nalog1\n";
            String nalog2 = "nalog2@gmail.com ,nalog2 ,Nalog2\n";
            String nalog3 = "nalog3@gmail.com ,nalog3 ,Nalog3\n";

            fout.write(nalog1.getBytes());
            fout.write(nalog2.getBytes());
            fout.write(nalog3.getBytes());

            fout.close();
        }
    }


}