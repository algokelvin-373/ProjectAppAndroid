package com.algokelvin.table;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int z = 0;

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
        TextView[] txt = new TextView[row * column];

        for (int x = 0; x < row * column; x++) {
            txt[x] = new TextView(this);
        }
        for (int y = 0; y < row * column; y += 2) {
            RowColumns rowColumns1;
            rowColumns1 = new RowColumns(this, txt[y], txt[y + 1]);

            rowColumns1.setTextViews(0, data[y - z][0]);

            rowColumns1.setGravityTextView(1, Gravity.END);
            rowColumns1.setTextViews(1, data[y - z][1]);

            rowColumns1.addTableData();
            tl.addView(rowColumns1.getTr());
            z++;
        }

    }
}