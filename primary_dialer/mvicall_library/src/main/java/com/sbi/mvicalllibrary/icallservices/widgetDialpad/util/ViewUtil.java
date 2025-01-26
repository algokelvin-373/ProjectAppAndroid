package com.sbi.mvicalllibrary.icallservices.widgetDialpad.util;

import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ListView;
import android.widget.TextView;

import com.sbi.mvicalllibrary.R;


public class ViewUtil {
    private ViewUtil() {
    }

    public static int getConstantPreLayoutWidth(View view) {

        final ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p.width < 0) {
            throw new IllegalStateException("Expecting view's width to be a constant rather " + "than a result of the layout pass");
        }
        return p.width;
    }


    public static boolean isViewLayoutRtl(View view) {
        return view.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    private static final ViewOutlineProvider OVAL_OUTLINE_PROVIDER = new ViewOutlineProvider() {
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, view.getWidth(), view.getHeight());
        }
    };


    public static void setupFloatingActionButton(View view, Resources res) {
        view.setOutlineProvider(OVAL_OUTLINE_PROVIDER);
        view.setTranslationZ(res.getDimensionPixelSize(R.dimen.floating_action_button_translation_z));
    }

    public static void addBottomPaddingToListViewForFab(ListView listView, Resources res) {
        final int fabPadding = res.getDimensionPixelSize(R.dimen.floating_action_button_list_bottom_padding);
        listView.setPaddingRelative(listView.getPaddingStart(), listView.getPaddingTop(), listView.getPaddingEnd(), listView.getPaddingBottom() + fabPadding);
        listView.setClipToPadding(false);
    }

    public static void resizeText(TextView textView, int originalTextSize, int minTextSize) {
        final Paint paint = textView.getPaint();
        final int width = textView.getWidth();
        if (width == 0) return;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, originalTextSize);
        float ratio = width / paint.measureText(textView.getText().toString());
        if (ratio <= 1.0f) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.max(minTextSize, originalTextSize * ratio));
        }
    }
}
