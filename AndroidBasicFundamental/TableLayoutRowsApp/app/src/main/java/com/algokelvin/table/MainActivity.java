package com.algokelvin.table;

import android.os.Bundle;
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

        TableLayout tl = (TableLayout) findViewById(R.id.tb_details);

        int row = data.length;
        int column = data[0].length;

        RowColumns rowColumns = new RowColumns(this, row, column);
        rowColumns.setTableLayout(tl);
        rowColumns.setTableData(data);

    }
}