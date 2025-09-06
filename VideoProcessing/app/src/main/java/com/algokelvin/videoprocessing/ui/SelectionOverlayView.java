package com.algokelvin.videoprocessing.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SelectionOverlayView extends View {
    private float startFrac = 0f;   // 0..1 dari total width
    private float endFrac   = 0.4f; // 0..1
    private float playFrac  = 0f;   // 0..1 position playhead (from start video)

    private boolean isPlaying = false;

    private final Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint dim = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint handle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint playhead = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float radius;
    private float strokeW;
    private float edgeGrabPx; // area tepi buat resize (biar gesture geser tak bentrok)

    // drag state
    private boolean draggingWindow = false;
    private float downX;
    private float downStart, downEnd;

    public interface WindowMoveListener {
        void onWindowMoved(float newStartFrac, float newEndFrac);
    }

    private WindowMoveListener moveListener;

    public SelectionOverlayView(Context c) {
        super(c);
        init();
    }

    public SelectionOverlayView(Context c, AttributeSet a) {
        super(c, a);
        init();
    }

    public SelectionOverlayView(Context c, AttributeSet a, int s) {
        super(c, a, s);
        init();
    }

    private void init() {
        strokeW = dp(2f);
        radius = dp(6f);
        edgeGrabPx = dp(24f);

        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(strokeW);
        stroke.setColor(Color.WHITE);

        dim.setStyle(Paint.Style.FILL);
        dim.setColor(0x88000000);

        handle.setStyle(Paint.Style.FILL);
        handle.setColor(Color.WHITE);

        playhead.setStyle(Paint.Style.STROKE);
        playhead.setStrokeWidth(dp(2f));    // Show play head
        playhead.setStrokeCap(Paint.Cap.ROUND);
        playhead.setColor(Color.WHITE);
        playhead.setDither(true);

        setWillNotDraw(false);
        setClickable(true); // agar bisa terima touch di atas RangeSlider
    }

    public void setWindowFractions(float start, float end) {
        start = clamp01(start);
        end   = clamp01(end);
        if (end < start) {
            float t = start; start = end; end = t;
        }
        startFrac = start;
        endFrac = end;
        invalidate();
    }

    public void setPlayheadFraction(float f) {
        playFrac = clamp01(f);
        if (isPlaying) {
            invalidate();
        }
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
        invalidate();
    }

    public void setOnWindowMoveListener(WindowMoveListener l) {
        this.moveListener = l;
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        float w = getWidth(), h = getHeight();
        float left  = startFrac * w;
        float right = endFrac   * w;

        // area dark in out box white
        c.drawRect(0, 0, left,  h, dim);
        c.drawRect(right, 0, w, h, dim);

        // box white (rounded)
        RectF r = new RectF(left, strokeW/2f, right, h - strokeW/2f);
        c.drawRoundRect(r, radius, radius, stroke);

        // pegangan kiri/kanan (opsional)
        float cy = h / 2f, hr = dp(6f);
        c.drawCircle(left,  cy, hr, handle);
        c.drawCircle(right, cy, hr, handle);

        // playhead tipis, hanya saat playing
        if (isPlaying) {
            float x = playFrac * w;
            // snap ke pixel untuk hasil tajam
            float xSnap = (float) Math.floor(x) + 0.5f;
            if (xSnap >= left && xSnap <= right) {
                c.drawLine(xSnap, strokeW, xSnap, h - strokeW, playhead);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float w = getWidth();
        float x = e.getX();
        float left  = startFrac * w;
        float right = endFrac   * w;

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("drag_box_video", "ACTION_DOWN");
                // jika tap di DALAM kotak (bukan di pinggir), aktifkan drag window
                if (x > left + edgeGrabPx && x < right - edgeGrabPx) {
                    draggingWindow = true;
                    downX = x;
                    downStart = startFrac;
                    downEnd = endFrac;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true; // overlay yang menangani
                }
                // biarkan RangeSlider yang menangani (untuk resize)
                draggingWindow = false;
                return false;

            case MotionEvent.ACTION_MOVE:
                Log.i("drag_box_video", "ACTION_MOVE");
                if (!draggingWindow) return false;
                float dx = (x - downX) / w; // delta dalam fraksi
                float winLen = (downEnd - downStart);
                float newStart = clamp01(downStart + dx);
                float newEnd = newStart + winLen;
                if (newEnd > 1f) { // dorong balik jika mentok kanan
                    newEnd = 1f;
                    newStart = newEnd - winLen;
                }
                if (newStart < 0f) { // dorong balik jika mentok kiri
                    newStart = 0f;
                    newEnd = winLen;
                }
                setWindowFractions(newStart, newEnd);
                if (moveListener != null) moveListener.onWindowMoved(newStart, newEnd);
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                draggingWindow = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                return true;
        }
        return super.onTouchEvent(e);
    }

    private static float clamp01(float v) {
        return v < 0 ? 0 : (v > 1 ? 1 : v);
    }

    private float dp(float v) {
        return v * getResources().getDisplayMetrics().density;
    }
}
