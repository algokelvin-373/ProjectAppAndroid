package algokelvin.app.animatorfeaturing;

import android.animation.ObjectAnimator;
import android.widget.TextView;

public class AnimatorController {
    public static final String TRANSLATE_X  = "translationX";

    private ObjectAnimator animator;

    public void animatorTextView(TextView idText, String animation, float coordinate, int duration) {
        this.animator = ObjectAnimator.ofFloat(idText, animation, coordinate);
        animator.setDuration(duration);
        animator.start();
    }

}
