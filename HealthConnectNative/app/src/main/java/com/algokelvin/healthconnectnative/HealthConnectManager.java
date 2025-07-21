package com.algokelvin.healthconnectnative;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.health.connect.client.HealthConnectClient;
import androidx.health.connect.client.HealthConnectFeatures;
import androidx.health.connect.client.PermissionController;

import java.util.Set;

public class HealthConnectManager {

    public enum HealthConnectAvailability {
        INSTALLED,
        NOT_INSTALLED,
        NOT_SUPPORTED
    }

    private static final int MIN_SUPPORTED_SDK = Build.VERSION_CODES.O_MR1;
    private final Context context;
    private final HealthConnectClient healthConnectClient;
    private HealthConnectAvailability availability = HealthConnectAvailability.NOT_SUPPORTED;

    public HealthConnectManager(Context context) {
        this.context = context.getApplicationContext();
        this.healthConnectClient = HealthConnectClient.getOrCreate(this.context);
        checkAvailability();
    }

    public boolean isSupported() {
        return Build.VERSION.SDK_INT >= MIN_SUPPORTED_SDK;
    }

    public HealthConnectAvailability getAvailability() {
        return availability;
    }

    public void checkAvailability() {
        int sdkStatus = HealthConnectClient.getSdkStatus(context);
        if (sdkStatus == HealthConnectClient.SDK_AVAILABLE) {
            availability = HealthConnectAvailability.INSTALLED;
        } else if (isSupported()) {
            availability = HealthConnectAvailability.NOT_INSTALLED;
        } else {
            availability = HealthConnectAvailability.NOT_SUPPORTED;
        }
    }

    public boolean isFeatureAvailable(int feature) {
        return healthConnectClient.getFeatures().getFeatureStatus(feature) == HealthConnectFeatures.FEATURE_STATUS_AVAILABLE;
    }

    public void enqueueReadStepWorker() {

    }

    public void showToast(@NonNull String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public ActivityResultContract<Set<String>, Set<String>> requestPermissionsActivityContract() {
        return PermissionController.createRequestPermissionResultContract();
    }
}
