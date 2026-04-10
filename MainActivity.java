package com.vaibhav.budgetflow;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editAmount;
    Button btnAdd;
    ListView listView;
    ArrayList<String> expenseList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editName = findViewById(R.id.editText_name);
        editAmount = findViewById(R.id.editText_amount);
        btnAdd = findViewById(R.id.button_add);
        listView = findViewById(R.id.expense_list);
        expenseList = new ArrayList<>();

        refreshList();

        btnAdd.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String amount = editAmount.getText().toString();
            
            if (!name.isEmpty() && !amount.isEmpty()) {
                if (myDb.insertData(name, amount)) {
                    Toast.makeText(this, "Expense Added!", Toast.LENGTH_SHORT).show();
                    editName.setText("");
                    editAmount.setText("");
                    refreshList();
                }
            } else {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshList() {
        expenseList.clear();
        Cursor cursor = myDb.getAllData();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                expenseList.add(cursor.getString(1) + "  ➤  ₹" + cursor.getString(2));
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseList);
        listView.setAdapter(adapter);
    }
}