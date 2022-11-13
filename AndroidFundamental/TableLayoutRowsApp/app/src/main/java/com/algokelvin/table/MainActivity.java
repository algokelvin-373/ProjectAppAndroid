package com.algokelvin.table;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample Data Details
        String[][] data = new String[3][2];
        data[0][0] = "Name Channel : "; data[0][1] = "Algokelvin";
        data[1][0] = "Name : "; data[1][1] = "Kelvin Herwanda Tandrio";
        data[2][0] = "Instagram : "; data[2][1] = "kelvin_373";

        // Set row and column from data 2 array
        int row = data.length;
        int column = data[0].length;

        // TableLayout Version 1
        TableLayout tl = findViewById(R.id.tb_details);
        RowColumns rowColumns = new RowColumns(this, row, column);
        rowColumns.setTableLayout(tl);
        rowColumns.setTableData(data);

        // TableLayout Version 2
        TableLayout tl2 = findViewById(R.id.tb_details_2);
        RowColumns rowColumns2 = new RowColumns(this, row, column, 8, 14);
        rowColumns2.setBgTextView(R.color.green, R.color.green);
        rowColumns2.setGravityTextView(0, Gravity.END);
        rowColumns2.setTextColors(R.color.red, R.color.blue);
        rowColumns2.setFontTextView(R.font.montserrat_regular, R.font.montserrat_regular);
        rowColumns2.setTableLayout(tl2);
        rowColumns2.setTableData2(data);

    }
}