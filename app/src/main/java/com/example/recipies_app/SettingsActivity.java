package com.example.recipies_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private ImageView ivBack;
    private LinearLayout llChangePhoto;
    private EditText etAge;
    private EditText etCaloricGoal;
    private Button btnSaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupClickListeners();
        loadCurrentSettings();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        llChangePhoto = findViewById(R.id.ll_change_photo);
        etAge = findViewById(R.id.et_age);
        etCaloricGoal = findViewById(R.id.et_caloric_goal);
        btnSaveSettings = findViewById(R.id.btn_save_settings);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfilePhoto();
            }
        });

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private void loadCurrentSettings() {
        // Cargar configuraciones actuales (datos de ejemplo)
        etAge.setText("25");
        etCaloricGoal.setText("2000");
    }

    private void changeProfilePhoto() {
        // Por ahora solo mostrar un mensaje
        Toast.makeText(this, "Funcionalidad de cambiar foto próximamente", Toast.LENGTH_SHORT).show();
        
        // TODO: Implementar selección de foto desde galería o cámara
        // Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void saveSettings() {
        String age = etAge.getText().toString().trim();
        String caloricGoal = etCaloricGoal.getText().toString().trim();

        if (validateSettings(age, caloricGoal)) {
            // TODO: Guardar configuraciones en SharedPreferences o base de datos
            // SharedPreferences prefs = getSharedPreferences("user_settings", MODE_PRIVATE);
            // SharedPreferences.Editor editor = prefs.edit();
            // editor.putString("age", age);
            // editor.putString("caloric_goal", caloricGoal);
            // editor.apply();

            Toast.makeText(this, "Configuraciones guardadas exitosamente", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean validateSettings(String age, String caloricGoal) {
        if (age.isEmpty()) {
            etAge.setError("La edad es obligatoria");
            etAge.requestFocus();
            return false;
        }

        if (caloricGoal.isEmpty()) {
            etCaloricGoal.setError("El objetivo calórico es obligatorio");
            etCaloricGoal.requestFocus();
            return false;
        }

        try {
            int ageValue = Integer.parseInt(age);
            int caloricValue = Integer.parseInt(caloricGoal);

            if (ageValue < 13 || ageValue > 120) {
                etAge.setError("Edad debe estar entre 13 y 120 años");
                etAge.requestFocus();
                return false;
            }

            if (caloricValue < 1000 || caloricValue > 5000) {
                etCaloricGoal.setError("Objetivo calórico debe estar entre 1000 y 5000");
                etCaloricGoal.requestFocus();
                return false;
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor ingresa valores numéricos válidos", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}