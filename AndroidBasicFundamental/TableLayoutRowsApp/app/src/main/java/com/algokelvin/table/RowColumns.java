package com.algokelvin.table;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

public class RowColumns {
    private final int rows, columns;
    private int z = 0;
    private TableLayout tl;
    private final Context ctx;
    private final TextView[] textViews;
    private int[] txtColors, sizesTextView, fontTextView, bgTextView, gravityTextView;

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

    public RowColumns(Context ctx, int r, int c, int m) {
        this.rows = r;
        this.columns = c;
        this.textViews = new TextView[rows * columns];
        this.ctx = ctx;
        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = new TextView(ctx);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f);
            lp.setMargins(0, m, 0, 0);
            textViews[i].setLayoutParams(lp);
        }
    }

    public void setSizesTextView(int ...sizes) {
        this.sizesTextView = sizes;
    }

    public void setFontTextView(int ...fontTextView) {
        this.fontTextView = fontTextView;
    }

    public void setBgTextView(int ...bgTextView) {
        this.bgTextView = bgTextView;
    }

    public void setGravityTextView(int ...gravityTextView) {
        this.gravityTextView = gravityTextView;
    }

    public void setTableData(String[][] data) {
        for (int y = 0; y < rows * columns; y += 2) {
            setTextViews(y, data[y - z][0]);

            setGravTextView(y + 1, Gravity.END);
            setTextViews(y + 1, data[y - z][1]);

            TableRow tr = new TableRow(ctx);
            tr.addView(textViews[y]);
            tr.addView(textViews[y + 1]);
            tl.addView(tr);
            z++;
        }
    }

    public void setTableData2(String[][] data) {
        for (int y = 0; y < rows * columns; y += 2) {
            setGravTextView(y + 1, gravityTextView[0]);
            setTextViews(y, data[y - z][0]);
            setSizeTextView(y, sizesTextView[0]);
            setColorTextView(y, txtColors[0]);
            setFontFamily(y, fontTextView[0]);
            setBackgroundTextView(y, bgTextView[0]);

            setGravTextView(y + 1, gravityTextView[1]);
            setTextViews(y + 1, data[y - z][1]);
            setSizeTextView(y + 1, sizesTextView[1]);
            setColorTextView(y + 1, txtColors[1]);
            setFontFamily(y + 1, fontTextView[1]);
            setBackgroundTextView(y + 1, bgTextView[1]);

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

    public void setTextColors(int ...colors) {
        this.txtColors = colors;
    }

    private void setTextViews(int xColumn, String txt) {
        textViews[xColumn].setText(txt);
    }

    private void setSizeTextView(int xColumn, float size) {
        textViews[xColumn].setTextSize(size);
    }

    private void setColorTextView(int xColumn, int color) {
        textViews[xColumn].setTextColor(ctx.getResources().getColor(color));
    }

    private void setBackgroundTextView(int xColumn, int background) {
        textViews[xColumn].setBackgroundResource(background);
    }

    private void setGravTextView(int xColumn, int gravity) {
        if (gravity != 0) {
            textViews[xColumn].setGravity(gravity);
        }
    }

    private void setFontFamily(int xColumn, int font) {
        if (font != 0) {
            textViews[xColumn].setTypeface(ResourcesCompat.getFont(ctx, font));
        }
    }

}
