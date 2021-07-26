package com.algokelvin.table;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RowColumns {
    private final int rows, columns;
    private int z = 0;
    private TableLayout tl;
    private final Context ctx;
    private final TextView[] textViews;

    public RowColumns(Context ctx, int r, int c) {
        this.rows = r;
        this.columns = c;
        this.textViews = new TextView[rows * columns];
        this.ctx = ctx;
        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = new TextView(ctx);
            textViews[i].setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f));
        }
    }

    public void setTableData(String[][] data) {
        for (int y = 0; y < rows * columns; y += 2) {
            setTextViews(y, data[y - z][0]);

            setGravityTextView(y + 1, Gravity.END);
            setTextViews(y + 1, data[y - z][1]);

            TableRow tr = new TableRow(ctx);
            tr.addView(textViews[y]);
            tr.addView(textViews[y + 1]);
            tl.addView(tr);
            z++;
        }
    }

    public void setTableLayout(TableLayout tl) {
        this.tl = tl;
    }

    private void setTextViews(int xColumn, String txt) {
        textViews[xColumn].setText(txt);
    }

    private void setGravityTextView(int xColumn, int gravity) {
        textViews[xColumn].setGravity(gravity);
    }

}
