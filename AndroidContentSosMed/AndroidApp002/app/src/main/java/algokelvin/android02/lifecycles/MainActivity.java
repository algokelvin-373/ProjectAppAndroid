package algokelvin.android02.lifecycles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CHAPTER_02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "This is onCreate Activity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "This is onStart Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "This is onResume Activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "This is onPause Activity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "This is onRestart Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "This is onDestroy Activity");
    }

}