package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnGetStarted = findViewById(R.id.btn_get_started);
        Button btnSkip = findViewById(R.id.btn_skip);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}