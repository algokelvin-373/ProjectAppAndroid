package com.algokelvin.primarydialer.mvicall;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.telecom.PhoneAccount;

import com.algokelvin.primarydialer.R;
import com.algokelvin.primarydialer.mvicall.common.MaterialColorMapUtils;


public class InCallUIMaterialColorMapUtils extends MaterialColorMapUtils {
    private final TypedArray sPrimaryColors;
    private final TypedArray sSecondaryColors;
    private final Resources mResources;

    public InCallUIMaterialColorMapUtils(Resources resources) {
        super(resources);
        sPrimaryColors = resources.obtainTypedArray(R.array.background_colors);
        sSecondaryColors = resources.obtainTypedArray(R.array.background_colors_dark);
        mResources = resources;
    }

    /**
     * Currently the InCallUI color will only vary by SIM color which is a list of colors
     * defined in the background_colors array, so first search the list for the matching color and
     * fall back to the closest matching color if an exact match does not exist.
     */
    @Override
    public MaterialPalette calculatePrimaryAndSecondaryColor(int color) {
        if (color == PhoneAccount.NO_HIGHLIGHT_COLOR) {
            return getDefaultPrimaryAndSecondaryColors(mResources);
        }

        for (int i = 0; i < sPrimaryColors.length(); i++) {
            if (sPrimaryColors.getColor(i, 0) == color) {
                return new MaterialPalette(
                        sPrimaryColors.getColor(i, 0),
                        sSecondaryColors.getColor(i, 0));
            }
        }

        // The color isn't in the list, so use the superclass to find an approximate color.
        return super.calculatePrimaryAndSecondaryColor(color);
    }

    public static MaterialPalette getDefaultPrimaryAndSecondaryColors(Resources resources) {
        final int primaryColor = resources.getColor(R.color.dialer_theme_color, null);
        final int secondaryColor = resources.getColor(R.color.dialer_theme_color_dark, null);
        return new MaterialPalette(primaryColor, secondaryColor);
    }
}