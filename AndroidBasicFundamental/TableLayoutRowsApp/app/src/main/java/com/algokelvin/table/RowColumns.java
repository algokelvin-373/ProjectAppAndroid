package com.algokelvin.table;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

public class RowColumns {
    private final TableRow tr;
    private final TextView[] textViews;

    public RowColumns(Context ctx, TextView... txt) {
        tr = new TableRow(ctx);
        this.textViews = txt;
        for (int i = 0; i < txt.length; i++) {
            textViews[i].setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f));
        }
    }

    public void setTextViews(int xColumn, String txt) {
        textViews[xColumn].setText(txt);
    }

    public void setGravityTextView(int xColumn, int gravity) {
        textViews[xColumn].setGravity(gravity);
    }

    public void addTableData() {
        for (TextView textView : textViews) {
            tr.addView(textView);
        }
    }

    public TableRow getTr() {
        return tr;
    }

}
