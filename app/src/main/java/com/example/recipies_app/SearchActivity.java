package com.example.recipies_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView recyclerViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        setupSearch();
        setupRecyclerView();
    }

    private void initViews() {
        searchEditText = findViewById(R.id.et_search);
        recyclerViewResults = findViewById(R.id.rv_search_results);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupRecyclerView() {
        recyclerViewResults.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void performSearch(String query) {
        // TODO: Implement search functionality
    }
}