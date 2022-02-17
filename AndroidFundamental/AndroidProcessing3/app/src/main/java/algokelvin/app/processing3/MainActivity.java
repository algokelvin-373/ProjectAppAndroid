package algokelvin.app.processing3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import algokelvin.app.processing3.databinding.ActivityMainBinding;
import processing.core.PApplet;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}