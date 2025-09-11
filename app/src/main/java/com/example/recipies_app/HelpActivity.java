package com.example.recipies_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    private ImageView ivBack;
    private LinearLayout llTechnicalSupport;
    private LinearLayout llChatBot;
    private LinearLayout llFAQ;
    private LinearLayout llTutorials;
    private LinearLayout llReportProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        llTechnicalSupport = findViewById(R.id.ll_technical_support);
        llChatBot = findViewById(R.id.ll_chat_bot);
        llFAQ = findViewById(R.id.ll_faq);
        llTutorials = findViewById(R.id.ll_tutorials);
        llReportProblem = findViewById(R.id.ll_report_problem);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llTechnicalSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTechnicalSupport();
            }
        });

        llChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatBot();
            }
        });

        llFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFAQ();
            }
        });

        llTutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTutorials();
            }
        });

        llReportProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportProblem();
            }
        });
    }

    private void callTechnicalSupport() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+1-800-RECIPES"));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No se puede realizar la llamada", Toast.LENGTH_SHORT).show();
        }
    }

    private void openChatBot() {
        Toast.makeText(this, "Abriendo Chat Bot de RecipeApp...", Toast.LENGTH_SHORT).show();
        // Aquí se podría implementar un chat bot real o navegar a una actividad de chat
        // Por ahora solo mostramos un mensaje
    }

    private void showFAQ() {
        Toast.makeText(this, "Preguntas Frecuentes", Toast.LENGTH_SHORT).show();
        // Aquí se podría navegar a una actividad de FAQ
    }

    private void showTutorials() {
        Toast.makeText(this, "Tutoriales de la aplicación", Toast.LENGTH_SHORT).show();
        // Aquí se podría navegar a una actividad de tutoriales
    }

    private void reportProblem() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:soporte@recipeapp.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Reporte de Problema - RecipeApp");
        intent.putExtra(Intent.EXTRA_TEXT, "Describe el problema que encontraste:\n\n");
        
        try {
            startActivity(Intent.createChooser(intent, "Enviar reporte"));
        } catch (Exception e) {
            Toast.makeText(this, "No se puede enviar el email", Toast.LENGTH_SHORT).show();
        }
    }
}