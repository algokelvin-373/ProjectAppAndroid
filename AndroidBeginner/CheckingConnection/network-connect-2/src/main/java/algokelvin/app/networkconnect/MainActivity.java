package algokelvin.app.networkconnect;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import algokelvin.app.networkconnect.databinding.ActivityMainBinding;

public class MainActivity extends ConnectionController {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isNetworkConnected();
        setConnection();
    }

    private void setConnection() {
        if (!isConnected) {
            binding.layoutConnection.txtMsgConnection.setText("Connection is Off");
            binding.layoutConnection.btnSetNetwork.setText("Setting Network");
            binding.layoutConnection.btnSetNetwork.setOnClickListener(v -> {
                Intent intentToSetNetwork = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intentToSetNetwork);
            });
        } else {
            binding.layoutConnection.layoutStatusConnect.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNetworkConnected();
        setConnection();
    }
}