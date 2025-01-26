package com.sbi.mvicalllibrary.icallservices.widgetDialpad.dialpad;

import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbi.mvicalllibrary.R;
import com.sbi.mvicalllibrary.icallservices.widgetDialpad.animation.AnimUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class DialpadView extends LinearLayout {
    private static final String TAG = DialpadView.class.getSimpleName();

    private static final double DELAY_MULTIPLIER = 0.66;
    private static final double DURATION_MULTIPLIER = 0.8;
    private final boolean mIsLandscape;
    private final boolean mIsRtl;

    private EditText mDigits;
    private ImageButton mDelete;
    private View mOverflowMenuButton;
    private final ColorStateList mRippleColor;

    private ViewGroup mRateContainer;
    private TextView mIldCountry;
    private TextView mIldRate;

    private boolean mCanDigitsBeEdited;

    private final int[] mButtonIds = new int[]{R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.star, R.id.pound};

    // For animation.
    private static final int KEY_FRAME_DURATION = 33;

    private final int mTranslateDistance;

    public DialpadView(Context context) {
        this(context, null);
    }

    public DialpadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialpadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        @SuppressLint("CustomViewStyleable") TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Dialpad);
        mRippleColor = a.getColorStateList(R.styleable.Dialpad_dialpad_key_button_touch_tint);
        a.recycle();

        mTranslateDistance = getResources().getDimensionPixelSize(R.dimen.dialpad_key_button_translate_y);

        mIsLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        mIsRtl = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        setupKeypad();
        mDigits = findViewById(R.id.digits);
        mDelete = findViewById(R.id.deleteButton);
        mOverflowMenuButton = findViewById(R.id.dialpad_overflow);
        mRateContainer = findViewById(R.id.rate_container);
        mIldCountry = mRateContainer.findViewById(R.id.ild_country);
        mIldRate = mRateContainer.findViewById(R.id.ild_rate);

        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager.isEnabled()) {
            mDigits.setSelected(true);
        }
    }

    private void setupKeypad() {
        final int[] letterIds = new int[]{R.string.dialpad_0_letters, R.string.dialpad_1_letters, R.string.dialpad_2_letters, R.string.dialpad_3_letters, R.string.dialpad_4_letters, R.string.dialpad_5_letters, R.string.dialpad_6_letters, R.string.dialpad_7_letters, R.string.dialpad_8_letters, R.string.dialpad_9_letters, R.string.dialpad_star_letters, R.string.dialpad_pound_letters};

        final Resources resources = getContext().getResources();

        DialpadKeyButton dialpadKey;
        TextView numberView;
        TextView lettersView;

        final Locale currentLocale = resources.getConfiguration().locale;
        final NumberFormat nf;

        if ("fa".equals(currentLocale.getLanguage())) {
            nf = DecimalFormat.getInstance(resources.getConfiguration().locale);
        } else {
            nf = DecimalFormat.getInstance(Locale.ENGLISH);
        }

        for (int i = 0; i < mButtonIds.length; i++) {
            dialpadKey = findViewById(mButtonIds[i]);
            numberView = dialpadKey.findViewById(R.id.dialpad_key_number);
            lettersView = dialpadKey.findViewById(R.id.dialpad_key_letters);

            final String numberString;
            final CharSequence numberContentDescription;
            if (mButtonIds[i] == R.id.pound) {
                numberString = resources.getString(R.string.dialpad_pound_number);
                numberContentDescription = numberString;
            } else if (mButtonIds[i] == R.id.star) {
                numberString = resources.getString(R.string.dialpad_star_number);
                numberContentDescription = numberString;
            } else {
                numberString = nf.format(i);
                String letters = resources.getString(letterIds[i]);
                Spannable spannable = Spannable.Factory.getInstance().newSpannable(numberString + "," + letters);
                spannable.setSpan((new TtsSpan.VerbatimBuilder(letters)).build(), numberString.length() + 1, numberString.length() + 1 + letters.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                numberContentDescription = spannable;
            }

            final RippleDrawable rippleBackground = (RippleDrawable) getDrawableCompat(getContext(), R.drawable.btn_dialpad_key);
            if (mRippleColor != null) {
                rippleBackground.setColor(mRippleColor);
            }

            numberView.setText(numberString);
            numberView.setElegantTextHeight(false);
            dialpadKey.setContentDescription(numberContentDescription);
            dialpadKey.setBackground(rippleBackground);

            if (lettersView != null) {
                lettersView.setText(resources.getString(letterIds[i]));
            }
        }

        final DialpadKeyButton one = findViewById(R.id.one);
        one.setLongHoverContentDescription(resources.getText(R.string.description_voicemail_button));

        final DialpadKeyButton zero = findViewById(R.id.zero);
        zero.setLongHoverContentDescription(resources.getText(R.string.description_image_button_plus));

    }

    private Drawable getDrawableCompat(Context context, int id) {
        return context.getDrawable(id);
    }

    public void setShowVoicemailButton(boolean show) {
        View view = findViewById(R.id.dialpad_key_voicemail);
        if (view != null) {
            view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setCanDigitsBeEdited(boolean canBeEdited) {
        View deleteButton = findViewById(R.id.deleteButton);
//        deleteButton.setVisibility(canBeEdited ? View.VISIBLE : View.GONE);
        View overflowMenuButton = findViewById(R.id.dialpad_overflow);
        overflowMenuButton.setVisibility(canBeEdited ? View.VISIBLE : View.GONE);

        EditText digits = findViewById(R.id.digits);
        digits.setClickable(canBeEdited);
        digits.setLongClickable(canBeEdited);
        digits.setFocusableInTouchMode(canBeEdited);
        digits.setCursorVisible(false);

        mCanDigitsBeEdited = canBeEdited;
    }

    public void setCallRateInformation(String countryName, String displayRate) {
        if (TextUtils.isEmpty(countryName) && TextUtils.isEmpty(displayRate)) {
            mRateContainer.setVisibility(View.GONE);
            return;
        }
        mRateContainer.setVisibility(View.VISIBLE);
        mIldCountry.setText(countryName);
        mIldRate.setText(displayRate);
    }

    public boolean canDigitsBeEdited() {
        return mCanDigitsBeEdited;
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        return true;
    }

    public void animateShow() {
        final AnimatorListenerAdapter showListener = new AnimatorListenerAdapter() {
        };

        for (int i = 0; i < mButtonIds.length; i++) {
            int delay = (int) (getKeyButtonAnimationDelay(mButtonIds[i]) * DELAY_MULTIPLIER);
            int duration = (int) (getKeyButtonAnimationDuration(mButtonIds[i]) * DURATION_MULTIPLIER);
            final DialpadKeyButton dialpadKey = findViewById(mButtonIds[i]);

            ViewPropertyAnimator animator = dialpadKey.animate();
            if (mIsLandscape) {
                dialpadKey.setTranslationX((mIsRtl ? -1 : 1) * mTranslateDistance);
                animator.translationX(0);
            } else {
                dialpadKey.setTranslationY(mTranslateDistance);
                animator.translationY(0);
            }
            animator.setInterpolator(AnimUtils.EASE_OUT_EASE_IN).setStartDelay(delay).setDuration(duration).setListener(showListener).start();
        }
    }

    public EditText getDigits() {
        return mDigits;
    }

    public ImageButton getDeleteButton() {
        return mDelete;
    }

    public View getOverflowMenuButton() {
        return mOverflowMenuButton;
    }

    private int getKeyButtonAnimationDelay(int buttonId) {
        if (mIsLandscape) {
            if (mIsRtl) {
                if (buttonId == R.id.three) {
                    return KEY_FRAME_DURATION * 1;
                } else if (buttonId == R.id.six) {
                    return KEY_FRAME_DURATION * 2;
                } else if (buttonId == R.id.nine) {
                    return KEY_FRAME_DURATION * 3;
                } else if (buttonId == R.id.pound) {
                    return KEY_FRAME_DURATION * 4;
                } else if (buttonId == R.id.two) {
                    return KEY_FRAME_DURATION * 5;
                } else if (buttonId == R.id.five) {
                    return KEY_FRAME_DURATION * 6;
                } else if (buttonId == R.id.eight) {
                    return KEY_FRAME_DURATION * 7;
                } else if (buttonId == R.id.zero) {
                    return KEY_FRAME_DURATION * 8;
                } else if (buttonId == R.id.one) {
                    return KEY_FRAME_DURATION * 9;
                } else if (buttonId == R.id.four) {
                    return KEY_FRAME_DURATION * 10;
                } else if (buttonId == R.id.seven || buttonId == R.id.star) {
                    return KEY_FRAME_DURATION * 11;
                }
            } else {
                if (buttonId == R.id.one) {
                    return KEY_FRAME_DURATION * 1;
                } else if (buttonId == R.id.four) {
                    return KEY_FRAME_DURATION * 2;
                } else if (buttonId == R.id.seven) {
                    return KEY_FRAME_DURATION * 3;
                } else if (buttonId == R.id.star) {
                    return KEY_FRAME_DURATION * 4;
                } else if (buttonId == R.id.two) {
                    return KEY_FRAME_DURATION * 5;
                } else if (buttonId == R.id.five) {
                    return KEY_FRAME_DURATION * 6;
                } else if (buttonId == R.id.eight) {
                    return KEY_FRAME_DURATION * 7;
                } else if (buttonId == R.id.zero) {
                    return KEY_FRAME_DURATION * 8;
                } else if (buttonId == R.id.three) {
                    return KEY_FRAME_DURATION * 9;
                } else if (buttonId == R.id.six) {
                    return KEY_FRAME_DURATION * 10;
                } else if (buttonId == R.id.nine || buttonId == R.id.pound) {
                    return KEY_FRAME_DURATION * 11;
                }
            }
        } else {
            if (buttonId == R.id.one) {
                return KEY_FRAME_DURATION * 1;
            } else if (buttonId == R.id.two) {
                return KEY_FRAME_DURATION * 2;
            } else if (buttonId == R.id.three) {
                return KEY_FRAME_DURATION * 3;
            } else if (buttonId == R.id.four) {
                return KEY_FRAME_DURATION * 4;
            } else if (buttonId == R.id.five) {
                return KEY_FRAME_DURATION * 5;
            } else if (buttonId == R.id.six) {
                return KEY_FRAME_DURATION * 6;
            } else if (buttonId == R.id.seven) {
                return KEY_FRAME_DURATION * 7;
            } else if (buttonId == R.id.eight) {
                return KEY_FRAME_DURATION * 8;
            } else if (buttonId == R.id.nine) {
                return KEY_FRAME_DURATION * 9;
            } else if (buttonId == R.id.star) {
                return KEY_FRAME_DURATION * 10;
            } else if (buttonId == R.id.zero || buttonId == R.id.pound) {
                return KEY_FRAME_DURATION * 11;
            }
        }

        Log.wtf(TAG, "Attempted to get animation delay for invalid key button id.");
        return 0;
    }

    private int getKeyButtonAnimationDuration(int buttonId) {
        if (mIsLandscape) {
            if (mIsRtl) {
                if (buttonId == R.id.one || buttonId == R.id.four || buttonId == R.id.seven || buttonId == R.id.star) {
                    return KEY_FRAME_DURATION * 8;
                } else if (buttonId == R.id.two || buttonId == R.id.five || buttonId == R.id.eight || buttonId == R.id.zero) {
                    return KEY_FRAME_DURATION * 9;
                } else if (buttonId == R.id.three || buttonId == R.id.six || buttonId == R.id.nine || buttonId == R.id.pound) {
                    return KEY_FRAME_DURATION * 10;
                }
            } else {
                if (buttonId == R.id.one || buttonId == R.id.four || buttonId == R.id.seven || buttonId == R.id.star) {
                    return KEY_FRAME_DURATION * 10;
                } else if (buttonId == R.id.two || buttonId == R.id.five || buttonId == R.id.eight || buttonId == R.id.zero) {
                    return KEY_FRAME_DURATION * 9;
                } else if (buttonId == R.id.three || buttonId == R.id.six || buttonId == R.id.nine || buttonId == R.id.pound) {
                    return KEY_FRAME_DURATION * 8;
                }
            }
        } else {
            if (buttonId == R.id.one || buttonId == R.id.two || buttonId == R.id.three || buttonId == R.id.four || buttonId == R.id.five || buttonId == R.id.six) {
                return KEY_FRAME_DURATION * 10;
            } else if (buttonId == R.id.seven || buttonId == R.id.eight || buttonId == R.id.nine) {
                return KEY_FRAME_DURATION * 9;
            } else if (buttonId == R.id.star || buttonId == R.id.zero || buttonId == R.id.pound) {
                return KEY_FRAME_DURATION * 8;
            }
        }

        Log.wtf(TAG, "Attempted to get animation duration for invalid key button id.");
        return 0;
    }
}
