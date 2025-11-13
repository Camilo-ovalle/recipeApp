package com.example.recipies_app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeStepsActivity extends AppCompatActivity {

    private ImageView ivClose;
    private TextView tvRecipeName;
    private TextView tvProgress;
    private ProgressBar progressBar;
    private TextView tvStepNumber;
    private TextView tvStepInstruction;
    private LinearLayout layoutTimer;
    private TextView tvTimerDisplay;
    private Button btnStartTimer;
    private Button btnPreviousStep;
    private Button btnNextStep;

    private List<RecipeStep> steps;
    private int currentStepIndex = 0;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Pantalla completa
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_recipe_steps);

        initViews();
        setupClickListeners();
        loadRecipeSteps();
        displayCurrentStep();
    }

    private void initViews() {
        ivClose = findViewById(R.id.iv_close);
        tvRecipeName = findViewById(R.id.tv_recipe_name);
        tvProgress = findViewById(R.id.tv_progress);
        progressBar = findViewById(R.id.progress_bar);
        tvStepNumber = findViewById(R.id.tv_step_number);
        tvStepInstruction = findViewById(R.id.tv_step_instruction);
        layoutTimer = findViewById(R.id.layout_timer);
        tvTimerDisplay = findViewById(R.id.tv_timer_display);
        btnStartTimer = findViewById(R.id.btn_start_timer);
        btnPreviousStep = findViewById(R.id.btn_previous_step);
        btnNextStep = findViewById(R.id.btn_next_step);
    }

    private void setupClickListeners() {
        ivClose.setOnClickListener(v -> {
            if (timerRunning) {
                stopTimer();
            }
            finish();
        });

        btnPreviousStep.setOnClickListener(v -> {
            if (currentStepIndex > 0) {
                stopTimer();
                currentStepIndex--;
                displayCurrentStep();
            }
        });

        btnNextStep.setOnClickListener(v -> {
            if (currentStepIndex < steps.size() - 1) {
                stopTimer();
                currentStepIndex++;
                displayCurrentStep();
            } else {
                // Último paso completado
                Toast.makeText(this, "¡Receta completada!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnStartTimer.setOnClickListener(v -> {
            if (timerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });
    }

    private void loadRecipeSteps() {
        // Obtener nombre de receta del intent (simulado)
        String recipeName = getIntent().getStringExtra("recipe_name");
        if (recipeName == null || recipeName.isEmpty()) {
            recipeName = "Pasta Carbonara";
        }
        tvRecipeName.setText(recipeName);

        // Pasos de ejemplo para Pasta Carbonara
        steps = new ArrayList<>();
        steps.add(new RecipeStep(1, "Poner a hervir una olla grande con agua y sal.", 0));
        steps.add(new RecipeStep(2, "Cocinar la pasta según las instrucciones del paquete.", 10));
        steps.add(new RecipeStep(3, "Mientras tanto, cortar la panceta en cubos pequeños y dorarla en una sartén.", 5));
        steps.add(new RecipeStep(4, "En un bowl, batir los huevos con el queso parmesano rallado.", 0));
        steps.add(new RecipeStep(5, "Escurrir la pasta guardando un poco del agua de cocción.", 0));
        steps.add(new RecipeStep(6, "Mezclar la pasta caliente con la panceta, retirar del fuego.", 0));
        steps.add(new RecipeStep(7, "Agregar la mezcla de huevo y queso, revolviendo rápidamente.", 0));
        steps.add(new RecipeStep(8, "Si es necesario, agregar un poco del agua de cocción para cremosidad.", 0));
        steps.add(new RecipeStep(9, "Servir inmediatamente con queso parmesano y pimienta negra.", 0));
    }

    private void displayCurrentStep() {
        RecipeStep currentStep = steps.get(currentStepIndex);

        // Actualizar número de paso
        tvStepNumber.setText("Paso " + currentStep.getStepNumber());
        tvStepInstruction.setText(currentStep.getInstruction());

        // Actualizar progreso
        int progress = (int) (((float) (currentStepIndex + 1) / steps.size()) * 100);
        progressBar.setProgress(progress);
        tvProgress.setText((currentStepIndex + 1) + " de " + steps.size());

        // Mostrar/ocultar timer
        if (currentStep.hasTimer()) {
            layoutTimer.setVisibility(View.VISIBLE);
            timeLeftInMillis = currentStep.getTimerMinutes() * 60 * 1000;
            updateTimerDisplay();
            btnStartTimer.setText("Iniciar Timer");
            timerRunning = false;
        } else {
            layoutTimer.setVisibility(View.GONE);
            stopTimer();
        }

        // Actualizar botones
        btnPreviousStep.setEnabled(currentStepIndex > 0);
        btnPreviousStep.setAlpha(currentStepIndex > 0 ? 1.0f : 0.5f);

        if (currentStepIndex == steps.size() - 1) {
            btnNextStep.setText("Finalizar");
        } else {
            btnNextStep.setText("Siguiente");
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerDisplay();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnStartTimer.setText("Reiniciar");
                Toast.makeText(RecipeStepsActivity.this, "¡Tiempo completado!", Toast.LENGTH_SHORT).show();
            }
        }.start();

        timerRunning = true;
        btnStartTimer.setText("Pausar");
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerRunning = false;
        btnStartTimer.setText("Reanudar");
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerRunning = false;
    }

    private void updateTimerDisplay() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimerDisplay.setText(timeFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
