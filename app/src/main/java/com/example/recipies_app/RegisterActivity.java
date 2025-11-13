package com.example.recipies_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipies_app.api.ApiResponse;
import com.example.recipies_app.api.RetrofitClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etEmail, etTelefono, etPassword, etConfirmPassword, etFechaNacimiento;
    private Spinner spGenero;
    private Button btnRegistrar, btnVolver;
    private ImageView btnBack;
    private Calendar calendarioSeleccionado;

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
        etEmail = findViewById(R.id.et_email);
        etTelefono = findViewById(R.id.et_telefono);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        etFechaNacimiento = findViewById(R.id.et_fecha_nacimiento);
        spGenero = findViewById(R.id.sp_genero);
        btnRegistrar = findViewById(R.id.btn_registrar);
        btnVolver = findViewById(R.id.btn_volver);
        btnBack = findViewById(R.id.btn_back);

        calendarioSeleccionado = Calendar.getInstance();
    }

    private void setupSpinner() {
        // Spinner de Género
        String[] generos = {
            "Seleccionar género",
            "Masculino",
            "Femenino",
            "Otro"
        };

        ArrayAdapter<String> adapterGeneros = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, generos);
        adapterGeneros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapterGeneros);
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

        etFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });
    }

    private void mostrarDatePicker() {
        int year = calendarioSeleccionado.get(Calendar.YEAR);
        int month = calendarioSeleccionado.get(Calendar.MONTH);
        int day = calendarioSeleccionado.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
            RegisterActivity.this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendarioSeleccionado.set(Calendar.YEAR, year);
                    calendarioSeleccionado.set(Calendar.MONTH, monthOfYear);
                    calendarioSeleccionado.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Formato YYYY-MM-DD para la API
                    String mes = String.format("%02d", monthOfYear + 1);
                    String dia = String.format("%02d", dayOfMonth);
                    String fechaSeleccionada = year + "-" + mes + "-" + dia;
                    etFechaNacimiento.setText(fechaSeleccionada);
                }
            },
            year, month, day
        );

        // Establecer fecha máxima como hoy (no puede nacer en el futuro)
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void registrarUsuario() {
        if (!validarCampos()) {
            return;
        }

        // Crear objeto User para enviar a la API
        User nuevoUsuario = new User();
        nuevoUsuario.setNombre(etNombre.getText().toString().trim());
        nuevoUsuario.setApellido(etApellido.getText().toString().trim());
        nuevoUsuario.setEmail(etEmail.getText().toString().trim());
        nuevoUsuario.setTelefono(etTelefono.getText().toString().trim());
        nuevoUsuario.setPassword(etPassword.getText().toString().trim());
        nuevoUsuario.setFechaNacimiento(etFechaNacimiento.getText().toString().trim());
        nuevoUsuario.setGenero(spGenero.getSelectedItem().toString());

        Toast.makeText(this, "Registrando usuario...", Toast.LENGTH_SHORT).show();

        // Llamada a la API
        Call<ApiResponse<User>> call = RetrofitClient.getInstance()
                .getApiService()
                .registerUser(nuevoUsuario);

        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<User> apiResponse = response.body();

                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        User usuarioRegistrado = apiResponse.getData();

                        Toast.makeText(RegisterActivity.this,
                                "¡Registro exitoso! Bienvenido " + usuarioRegistrado.getNombre(),
                                Toast.LENGTH_LONG).show();

                        // Navegar a HomeActivity o LoginActivity
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        String error = apiResponse.getError() != null ? apiResponse.getError() : "Error desconocido";
                        Toast.makeText(RegisterActivity.this,
                                "Error: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Error al registrar usuario (código: " + response.code() + ")",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validarCampos() {
        // Nombre
        if (etNombre.getText().toString().trim().isEmpty()) {
            etNombre.setError("Campo requerido");
            etNombre.requestFocus();
            return false;
        }

        // Apellido
        if (etApellido.getText().toString().trim().isEmpty()) {
            etApellido.setError("Campo requerido");
            etApellido.requestFocus();
            return false;
        }

        // Email
        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Campo requerido");
            etEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            etEmail.setError("Email inválido");
            etEmail.requestFocus();
            return false;
        }

        // Teléfono
        if (etTelefono.getText().toString().trim().isEmpty()) {
            etTelefono.setError("Campo requerido");
            etTelefono.requestFocus();
            return false;
        }

        // Password
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

        // Confirm Password
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

        // Género
        if (spGenero.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Por favor selecciona un género", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Fecha de Nacimiento
        if (etFechaNacimiento.getText().toString().trim().isEmpty()) {
            etFechaNacimiento.setError("Campo requerido");
            etFechaNacimiento.requestFocus();
            return false;
        }

        return true;
    }
}