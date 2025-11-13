package com.example.recipies_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ImageView ivShare;
    private TextView tvEmptyState;
    private TextView tvStats;
    private RecyclerView rvShoppingList;
    private FloatingActionButton fabAddItem;
    private ShoppingListAdapter adapter;
    private List<ShoppingItem> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        initViews();
        setupClickListeners();
        setupRecyclerView();
        loadShoppingItems();
        updateStats();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        ivShare = findViewById(R.id.iv_share);
        tvEmptyState = findViewById(R.id.tv_empty_state);
        tvStats = findViewById(R.id.tv_stats);
        rvShoppingList = findViewById(R.id.rv_shopping_list);
        fabAddItem = findViewById(R.id.fab_add_item);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> finish());

        ivShare.setOnClickListener(v -> shareShoppingList());

        fabAddItem.setOnClickListener(v -> showAddItemDialog());
    }

    private void setupRecyclerView() {
        shoppingList = new ArrayList<>();
        adapter = new ShoppingListAdapter(shoppingList, this, position -> {
            shoppingList.remove(position);
            adapter.notifyItemRemoved(position);
            updateStats();
            checkEmptyState();
        });
        adapter.setOnItemCheckListener(() -> updateStats());
        rvShoppingList.setLayoutManager(new LinearLayoutManager(this));
        rvShoppingList.setAdapter(adapter);
    }

    private void loadShoppingItems() {
        // Items de ejemplo
        shoppingList.add(new ShoppingItem("Tomates", "500g", "Verduras"));
        shoppingList.add(new ShoppingItem("Pasta", "1 paquete", "Granos"));
        shoppingList.add(new ShoppingItem("Aceite de oliva", "1 botella", "Aceites"));
        shoppingList.add(new ShoppingItem("Ajo", "3 dientes", "Verduras"));
        shoppingList.add(new ShoppingItem("Queso parmesano", "200g", "Lácteos"));
        shoppingList.add(new ShoppingItem("Pollo", "1kg", "Carnes"));
        shoppingList.add(new ShoppingItem("Lechuga", "1 unidad", "Verduras"));
        shoppingList.add(new ShoppingItem("Limón", "3 unidades", "Frutas"));

        adapter.notifyDataSetChanged();
        checkEmptyState();
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        builder.setView(dialogView);

        EditText etItemName = dialogView.findViewById(R.id.et_item_name);
        EditText etItemQuantity = dialogView.findViewById(R.id.et_item_quantity);
        Spinner spinnerCategory = dialogView.findViewById(R.id.spinner_category);

        // Configurar spinner de categorías
        String[] categories = {"Verduras", "Frutas", "Carnes", "Lácteos", "Granos", "Aceites", "Otros"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        builder.setTitle("Agregar Item")
                .setPositiveButton("Agregar", (dialog, which) -> {
                    String name = etItemName.getText().toString().trim();
                    String quantity = etItemQuantity.getText().toString().trim();
                    String category = spinnerCategory.getSelectedItem().toString();

                    if (!name.isEmpty() && !quantity.isEmpty()) {
                        ShoppingItem newItem = new ShoppingItem(name, quantity, category);
                        shoppingList.add(0, newItem);
                        adapter.notifyItemInserted(0);
                        rvShoppingList.scrollToPosition(0);
                        updateStats();
                        checkEmptyState();
                    } else {
                        Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void shareShoppingList() {
        if (shoppingList.isEmpty()) {
            Toast.makeText(this, "La lista está vacía", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder listText = new StringBuilder("📝 Mi Lista de Compras\n\n");

        for (ShoppingItem item : shoppingList) {
            String checkbox = item.isChecked() ? "✅" : "☐";
            listText.append(checkbox).append(" ")
                    .append(item.getName()).append(" - ")
                    .append(item.getQuantity()).append(" (")
                    .append(item.getCategory()).append(")\n");
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Mi Lista de Compras");
        shareIntent.putExtra(Intent.EXTRA_TEXT, listText.toString());
        startActivity(Intent.createChooser(shareIntent, "Compartir lista vía"));
    }

    private void updateStats() {
        int total = shoppingList.size();
        int checked = 0;
        for (ShoppingItem item : shoppingList) {
            if (item.isChecked()) {
                checked++;
            }
        }
        tvStats.setText(checked + " de " + total + " items completados");
    }

    private void checkEmptyState() {
        if (shoppingList.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvShoppingList.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvShoppingList.setVisibility(View.VISIBLE);
        }
    }
}
