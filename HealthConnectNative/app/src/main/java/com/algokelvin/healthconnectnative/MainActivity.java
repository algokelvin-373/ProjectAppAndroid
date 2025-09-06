package com.algokelvin.healthconnectnative;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.health.connect.client.permission.HealthPermission;
import androidx.health.connect.client.records.ExerciseSessionRecord;
import androidx.health.connect.client.records.HeartRateRecord;
import androidx.health.connect.client.records.StepsRecord;
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord;

import java.util.HashSet;
import java.util.Set;

import static kotlin.jvm.JvmClassMappingKt.getKotlinClass;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HealthConnectSync";
    private HealthConnectManager healthConnectManager;
    private ActivityResultLauncher<Set<String>> permissionsLauncher;
    private boolean isHealthConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView statusText = findViewById(R.id.statusText);

        healthConnectManager = new HealthConnectManager(getApplicationContext());
        HealthConnectManager.HealthConnectAvailability status = healthConnectManager.getAvailability();

        statusText.setText("Status: "+ status.name());

        // Inisialisasi permission launcher
        permissionsLauncher = registerForActivityResult(
                healthConnectManager.requestPermissionsActivityContract(),
                grantedPermissions -> {
                    onPermissionsResult(grantedPermissions);
                }
        );
        Button btnRequestPermission = findViewById(R.id.requestPermissionButton);
        btnRequestPermission.setOnClickListener(v -> {
            Set<String> permissions = new HashSet<>();

            permissions.add(HealthPermission.getWritePermission(getKotlinClass(ExerciseSessionRecord.class)));
            permissions.add(HealthPermission.getReadPermission(getKotlinClass(ExerciseSessionRecord.class)));
            permissions.add(HealthPermission.getWritePermission(getKotlinClass(StepsRecord.class)));
            permissions.add(HealthPermission.getReadPermission(getKotlinClass(StepsRecord.class)));
            permissions.add(HealthPermission.getWritePermission(getKotlinClass(TotalCaloriesBurnedRecord.class)));
            permissions.add(HealthPermission.getWritePermission(getKotlinClass(HeartRateRecord.class)));

            // Launch permission dialog
            permissionsLauncher.launch(permissions);
        });
    }

    private void onPermissionsResult(Set<String> grantedPermissions) {
        if (grantedPermissions != null && !grantedPermissions.isEmpty()) {
            Toast.makeText(this, "Permissions granted: " + grantedPermissions.size(), Toast.LENGTH_SHORT).show();
            isHealthConnected = true;
        } else {
            Toast.makeText(this, "No permissions granted", Toast.LENGTH_SHORT).show();
        }
    }

}