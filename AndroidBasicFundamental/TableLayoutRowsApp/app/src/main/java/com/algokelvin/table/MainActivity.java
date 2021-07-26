package com.algokelvin.table;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tl = (TableLayout) findViewById(R.id.tb_details);

        TextView txt = new TextView(this);
        TextView txt1 = new TextView(this);
        RowColumns rowColumns1 = new RowColumns(this, txt, txt1);
        rowColumns1.setTextViews(0, "Title");
        rowColumns1.setGravityTextView(1, Gravity.END);
        rowColumns1.setTextViews(1, "Data");
        rowColumns1.addTableData();
        tl.addView(rowColumns1.getTr());

        TextView txt3 = new TextView(this);
        TextView txt4 = new TextView(this);
        RowColumns rowColumns2 = new RowColumns(this, txt3, txt4);
        rowColumns2.setTextViews(0, "Title 1");
        rowColumns2.setGravityTextView(1, Gravity.END);
        rowColumns2.setTextViews(1, "Data 1");
        rowColumns2.addTableData();
        tl.addView(rowColumns2.getTr());

    }
}