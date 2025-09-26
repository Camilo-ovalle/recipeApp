package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etEdad, etDocumento, etTelefono, etPesoActual, etAltura, etPassword, etConfirmPassword;
    private Spinner spObjetivo;
    private Button btnRegistrar, btnVolver;
    private ImageView btnBack;

    private DatePicker calendarDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupSpinner();
        setupClickListeners();
    }

    private void initViews() {
        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etEdad = findViewById(R.id.et_edad);
        etDocumento = findViewById(R.id.et_documento);
        etTelefono = findViewById(R.id.et_telefono);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        etPesoActual = findViewById(R.id.et_peso_actual);
        etAltura = findViewById(R.id.et_altura);
        spObjetivo = findViewById(R.id.sp_objetivo);
        btnRegistrar = findViewById(R.id.btn_registrar);
        btnVolver = findViewById(R.id.btn_volver);
        btnBack = findViewById(R.id.btn_back);
        calendarDate = findViewById(R.id.datePicker);
    }

    private void setupSpinner() {
        String[] objetivos = {
            "Seleccionar objetivo",
            "Perder peso",
            "Mantener peso",
            "Ganar peso",
            "Ganar masa muscular",
            "Mejorar salud general"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, objetivos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spObjetivo.setAdapter(adapter);
    }

    private void setupClickListeners() {
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registrarUsuario() {
        if (!validarCampos()) {
            return;
        }

        try {
            JSONObject usuario = new JSONObject();
            usuario.put("id", UUID.randomUUID().toString());
            usuario.put("nombre", etNombre.getText().toString().trim());
            usuario.put("apellido", etApellido.getText().toString().trim());
            usuario.put("edad", Integer.parseInt(etEdad.getText().toString().trim()));
            usuario.put("documento", etDocumento.getText().toString().trim());
            usuario.put("telefono", etTelefono.getText().toString().trim());
            usuario.put("password", etPassword.getText().toString().trim());
            usuario.put("pesoActual", Float.parseFloat(etPesoActual.getText().toString().trim()));
            usuario.put("altura", Float.parseFloat(etAltura.getText().toString().trim()));
            usuario.put("objetivo", spObjetivo.getSelectedItem().toString());
            usuario.put("fechaRegistro", System.currentTimeMillis());

            guardarUsuarioEnArchivo(usuario);

            // Mostrar ruta del archivo para debug
            String filePath = this.getFilesDir().getAbsolutePath() + "/data.json";
            Toast.makeText(this, "Archivo guardado en: " + filePath, Toast.LENGTH_SHORT).show();

            // Verificar contenido del archivo
            verificarContenidoArchivo();

            // Navegar a HomeActivity después del registro exitoso
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } catch (JSONException | NumberFormatException e) {
            Toast.makeText(this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCampos() {
        if (etNombre.getText().toString().trim().isEmpty()) {
            etNombre.setError("Campo requerido");
            etNombre.requestFocus();
            return false;
        }

        if (etApellido.getText().toString().trim().isEmpty()) {
            etApellido.setError("Campo requerido");
            etApellido.requestFocus();
            return false;
        }

        if (etEdad.getText().toString().trim().isEmpty()) {
            etEdad.setError("Campo requerido");
            etEdad.requestFocus();
            return false;
        }

        if (etDocumento.getText().toString().trim().isEmpty()) {
            etDocumento.setError("Campo requerido");
            etDocumento.requestFocus();
            return false;
        }

        if (etTelefono.getText().toString().trim().isEmpty()) {
            etTelefono.setError("Campo requerido");
            etTelefono.requestFocus();
            return false;
        }

        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError("Campo requerido");
            etPassword.requestFocus();
            return false;
        }

        if (etPassword.getText().toString().trim().length() < 6) {
            etPassword.setError("La contraseña debe tener al menos 6 caracteres");
            etPassword.requestFocus();
            return false;
        }

        if (etConfirmPassword.getText().toString().trim().isEmpty()) {
            etConfirmPassword.setError("Campo requerido");
            etConfirmPassword.requestFocus();
            return false;
        }

        if (!etPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
            etConfirmPassword.setError("Las contraseñas no coinciden");
            etConfirmPassword.requestFocus();
            return false;
        }

        if (etPesoActual.getText().toString().trim().isEmpty()) {
            etPesoActual.setError("Campo requerido");
            etPesoActual.requestFocus();
            return false;
        }

        if (etAltura.getText().toString().trim().isEmpty()) {
            etAltura.setError("Campo requerido");
            etAltura.requestFocus();
            return false;
        }

        if (spObjetivo.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Por favor selecciona un objetivo", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void guardarUsuarioEnArchivo(JSONObject usuario) {
        try {
            JSONArray usuarios = leerUsuariosDelArchivo();
            usuarios.put(usuario);

            FileOutputStream fos = openFileOutput("data.json", MODE_PRIVATE);
            fos.write(usuarios.toString().getBytes("UTF-8"));
            fos.flush();
            fos.close();

            // Verificar que se guardó correctamente
            Toast.makeText(this, "Datos guardados correctamente. Total usuarios: " + usuarios.length(),
                Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private JSONArray leerUsuariosDelArchivo() {
        try {
            FileInputStream fis = openFileInput("data.json");
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();

            String jsonString = new String(buffer, "UTF-8");
            return new JSONArray(jsonString);

        } catch (IOException | JSONException e) {
            return new JSONArray();
        }
    }

    private void limpiarFormulario() {
        etNombre.setText("");
        etApellido.setText("");
        etEdad.setText("");
        etDocumento.setText("");
        etTelefono.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
        etPesoActual.setText("");
        etAltura.setText("");
        spObjetivo.setSelection(0);
    }

    // Método para verificar el contenido del archivo (solo para debug)
    private void verificarContenidoArchivo() {
        try {
            FileInputStream fis = openFileInput("data.json");
            int size = fis.available();
            if (size > 0) {
                byte[] buffer = new byte[size];
                fis.read(buffer);
                fis.close();
                String content = new String(buffer, "UTF-8");
                Toast.makeText(this, "Contenido: " + content, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Archivo vacío", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "No se pudo leer el archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}