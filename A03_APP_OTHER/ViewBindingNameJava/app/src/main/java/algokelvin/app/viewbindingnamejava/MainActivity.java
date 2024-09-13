package algokelvin.app.viewbindingnamejava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import algokelvin.app.viewbindingnamejava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAction.setOnClickListener(view -> {
            String name = binding.edtName.getText().toString();
            binding.txtName.setText(getString(R.string.data_name, name));
        });

    }
}