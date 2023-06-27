package com.example.myapplication.presentation.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class DeepLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);  // Use your layout file for this activity

        // Get the intent that started this activity
        Intent intent = getIntent();
        if (intent != null && intent.getAction().equals(Intent.ACTION_VIEW)) {
            Uri data = intent.getData();
            // Now 'data' contains the deep link URL. You can parse it and use it however you want.
            // As an example, we're showing it in a Toast:
            Toast.makeText(this, "Deep link: " + data, Toast.LENGTH_LONG).show();
        }
    }
}
