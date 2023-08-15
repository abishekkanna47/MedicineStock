package com.example.medicinestock;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private EditText medicineNameEditText, expiryDateEditText, priceEditText;
    private Button addButton, viewButton,updatebutton,delbutton;
    private ListView medicineListView;
    private ArrayAdapter<Medicine> medicineArrayAdapter;
    private List<Medicine> medicineList;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        medicineNameEditText = findViewById(R.id.medicineNameEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        priceEditText = findViewById(R.id.priceEditText);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        medicineListView = findViewById(R.id.medicineListView);
updatebutton=findViewById(R.id.up);
delbutton=findViewById(R.id.del);
        // Initialize SQLite database
        database = openOrCreateDatabase("MedicineDB", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS medicines (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, expiryDate TEXT, price REAL)");

        // Initialize data
        medicineList = new ArrayList<>();
        medicineArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicineList);
        medicineListView.setAdapter(medicineArrayAdapter);
        delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletemed();
            }
        });
updatebutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        updatemed();
    }
});
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicine();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMedicines();
            }
        });

        medicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Handle item click (Update or Delete)
            }
        });
    }
    long rs;
    public void updatemed(){

        String name = medicineNameEditText.getText().toString();
        String expiryDate = expiryDateEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());
        ContentValues con_val=new ContentValues();
        con_val.put("expiryDate",expiryDate);
        con_val.put("price",price);

        Cursor cursor=database.rawQuery("Select * from medicines where name=?",new String[]{name});
        if(cursor.getCount()>0) {
            rs = database.update("medicines", con_val, "name=?", new String[]{name});
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            medicineNameEditText.setText("");
            expiryDateEditText.setText("");
            priceEditText.setText("");
        }
            else {
            Toast.makeText(this, "cant update", Toast.LENGTH_SHORT).show();
            medicineNameEditText.setText("");
            expiryDateEditText.setText("");
            priceEditText.setText("");
        }        if(rs==-1)
        {
            Toast.makeText(this, "cant update", Toast.LENGTH_SHORT).show();
            medicineNameEditText.setText("");
            expiryDateEditText.setText("");
            priceEditText.setText("");
        }
        Toast.makeText(this, "updated successfully", Toast.LENGTH_SHORT).show();
        medicineNameEditText.setText("");
        expiryDateEditText.setText("");
        priceEditText.setText("");
    }
    public void deletemed(){
        String name = medicineNameEditText.getText().toString();

        Cursor cursor=database.rawQuery("Select * from medicines where name=?",new String[]{name});
        if(cursor.getCount()>0) {
            rs = database.delete("medicines", "name=?", new String[]{name});
            Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
            medicineNameEditText.setText("");
            expiryDateEditText.setText("");
            priceEditText.setText("");
        }
        else{   Toast.makeText(this, "cant delete", Toast.LENGTH_SHORT).show();
            medicineNameEditText.setText("");
            expiryDateEditText.setText("");
            priceEditText.setText("");}
        if(rs==-1) {   Toast.makeText(this, "cant delete", Toast.LENGTH_SHORT).show();
            medicineNameEditText.setText("");
            expiryDateEditText.setText("");
            priceEditText.setText("");}
        Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
        medicineNameEditText.setText("");
        expiryDateEditText.setText("");
        priceEditText.setText("");
    }
    private void addMedicine() {
        String name = medicineNameEditText.getText().toString();
        String expiryDate = expiryDateEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("expiryDate", expiryDate);
        contentValues.put("price", price);

        long result = database.insert("medicines", null, contentValues);

        if (result != -1) {
            Toast.makeText(this, "Medicine added successfully", Toast.LENGTH_SHORT).show();
            medicineNameEditText.setText("");
            expiryDateEditText.setText("");
            priceEditText.setText("");
        } else {
            Toast.makeText(this, "Error adding medicine", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewMedicines() {
        Cursor cursor = database.rawQuery("SELECT * FROM medicines ORDER BY expiryDate ASC", null);

        if (cursor.moveToFirst()) {
            medicineList.clear();
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String expiryDate = cursor.getString(cursor.getColumnIndex("expiryDate"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                Medicine medicine = new Medicine();
                medicine.setId(id);
                medicine.setName(name);
                medicine.setExpiryDate(expiryDate);
                medicine.setPrice(price);

                medicineList.add(medicine);
            } while (cursor.moveToNext());

            medicineArrayAdapter.notifyDataSetChanged();
        }

        cursor.close();
    }
}
