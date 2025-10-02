package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout llMyRecipes;
    private LinearLayout llFavorites;
    private LinearLayout llSettings;
    private LinearLayout llHelp;
    private LinearLayout llAbout;
    private TextView tvUserName;
    private TextView tvUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        loadUserData();
        setupClickListeners();
    }

    private void initViews() {
        llMyRecipes = findViewById(R.id.ll_my_recipes);
        llFavorites = findViewById(R.id.ll_favorites);
        llSettings = findViewById(R.id.ll_settings);
        llHelp = findViewById(R.id.ll_help);
        llAbout = findViewById(R.id.ll_about);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserInfo = findViewById(R.id.tv_user_info);
    }

    private void loadUserData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("nombre")) {
            String nombre = intent.getStringExtra("nombre");
            String apellido = intent.getStringExtra("apellido");
            String edad = intent.getStringExtra("edad");
            String objetivo = intent.getStringExtra("objetivo");
            String pesoActual = intent.getStringExtra("pesoActual");
            String altura = intent.getStringExtra("altura");
            String fechaNacimiento = intent.getStringExtra("fechaNacimiento");

            // Actualizar nombre completo
            String nombreCompleto = nombre + " " + apellido;
            tvUserName.setText(nombreCompleto);

            // Actualizar información del usuario
            String infoUsuario = "";
            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                infoUsuario = fechaNacimiento + " • " + objetivo;
            } else {
                infoUsuario = edad + " años • " + objetivo;
            }
            tvUserInfo.setText(infoUsuario);
        }
    }

    private void setupClickListeners() {
        llMyRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MyRecipesActivity.class);
                startActivity(intent);
            }
        });

        llFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}