package algokelvin.app.todolist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edtToDo;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddToDo = findViewById(R.id.btn_add_to_do);
        ListView listToDo = findViewById(R.id.list_to_do);

        edtToDo = findViewById(R.id.edt_to_do);

        itemList = FileHelper.readData(this);
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itemList
        );
        listToDo.setAdapter(arrayAdapter);

        btnAddToDo.setOnClickListener(v -> {
            String itemName = edtToDo.getText().toString();
            itemList.add(itemName);
            edtToDo.setText("");
            FileHelper.writeData(itemList, getApplicationContext());
            arrayAdapter.notifyDataSetChanged();
        });

        listToDo.setOnItemClickListener((adapterView, view, position, l) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Delete");
            alert.setMessage("Do you want to delete this item?");
            alert.setCancelable(false);

            alert.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
            alert.setPositiveButton("Yes", (dialogInterface, i) -> {
                itemList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                FileHelper.writeData(itemList, getApplicationContext());
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });

    }
}