package algokelvin.app.processing3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import algokelvin.app.processing3.databinding.ActivityMainBinding;
import processing.core.PApplet;
import processing.android.PFragment;
import processing.android.CompatUtils;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        sketch = new MainProcessing3();
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (sketch != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }

}