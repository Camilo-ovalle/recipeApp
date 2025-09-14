package com.example.recipies_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initViews();
        setupClickListeners();
        //setupAppInfo();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        tvAppVersion = findViewById(R.id.tv_app_version);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupAppInfo() {
        // Obtener versión de la app
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tvAppVersion.setText("Versión " + versionName);
        } catch (Exception e) {
            tvAppVersion.setText("Versión 1.0.0");
        }
    }
}