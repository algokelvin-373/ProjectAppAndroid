package com.sbi.mvicalllibrary.icallservices.widgetDialpad.dialpad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class DialpadTextView extends TextView {
    private final Rect mTextBounds = new Rect();
    private String mTextStr;

    public DialpadTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Draw the text to fit within the height/width which have been specified during measurement.
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        Paint paint = getPaint();

        // Without this, the draw does not respect the style's specified text color.
        paint.setColor(getCurrentTextColor());

        // The text bounds values are relative and can be negative,, so rather than specifying a
        // standard origin such as 0, 0, we need to use negative of the left/top bounds.
        // For example, the bounds may be: Left: 11, Right: 37, Top: -77, Bottom: 0
        canvas.drawText(mTextStr, -mTextBounds.left, -mTextBounds.top, paint);
    }

    /**
     * Calculate the pixel-accurate bounds of the text when rendered, and use that to specify the
     * height and width.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTextStr = getText().toString();
        getPaint().getTextBounds(mTextStr, 0, mTextStr.length(), mTextBounds);

        int width = resolveSize(mTextBounds.width(), widthMeasureSpec);
        int height = resolveSize(mTextBounds.height(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
