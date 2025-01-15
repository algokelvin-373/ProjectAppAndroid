package com.algokelvin.primarydialer

import android.Manifest
import android.app.AlertDialog
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telecom.PhoneAccount
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.algokelvin.primarydialer.databinding.ActivityMainInCallBinding

class MainInCallActivity : AppCompatActivity() {
    private val TAG = "PermissionHelper"
    private lateinit var binding: ActivityMainInCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainInCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDefaultDialer()
        checkAndRequestPermissions()

        //Activate dialer
        binding.btnActiveDialer.setOnClickListener { v: View? ->
            requestRolePermission()
            registerPhoneAccount()
        }

        binding.callButton.setOnClickListener { view: View? ->
            makeCall(binding.phoneNumberInput.text.toString())
        }
    }

    private fun checkAndRequestPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_PHONE_ROLE
            )
        }
    }

    private fun requestRolePermission() {
        val permissionHelper: PermissionHelper = PermissionHelper()
        permissionHelper.setPermission(this)
    }

    private fun registerPhoneAccount() {
        val telecomManager = getSystemService(TELECOM_SERVICE) as TelecomManager
        val componentName: ComponentName = ComponentName(this, MyInCallServiceImpl::class.java)
        val phoneAccountHandle = PhoneAccountHandle(componentName, "DefaultDialer")
        val phoneAccount = PhoneAccount.builder(phoneAccountHandle, "Default Dialer")
            .setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER)
            .build()
        telecomManager.registerPhoneAccount(phoneAccount)
    }

    private fun setDefaultDialer() {
        val telecomManager = getSystemService(TELECOM_SERVICE) as TelecomManager
        if (telecomManager != null && packageName != telecomManager.defaultDialerPackage) {
            val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
            intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
            startActivity(intent)
        }
    }

    private fun makeCall(phoneNumber: String?) {
        val telecomManager = getSystemService(TELECOM_SERVICE) as TelecomManager
        if (telecomManager != null && phoneNumber != null) {
            val uri = Uri.fromParts("tel", phoneNumber, null)
            val extras = Bundle()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            telecomManager.placeCall(uri, extras)
        } else {
            // Jika TelecomManager gagal, gunakan fallback
            val intent = Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
    }


    //Handle the role permission result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("PermissionHelper", "11 Successfully set as default dialer")
        Log.d("PermissionHelper", "RequestCode: $requestCode")
        Log.d("PermissionHelper", "resultCode: $resultCode")
        if (requestCode == REQUEST_PHONE_ROLE) {
            if (resultCode == RESULT_OK) {
                // Berhasil menjadi aplikasi telepon default
                Log.d("PermissionHelper", "Successfully set as default dialer")

                // Tambahkan dialog atau toast untuk memberi tahu pengguna
                AlertDialog.Builder(this)
                    .setTitle("Aplikasi Telepon")
                    .setMessage("Aplikasi ini sekarang menjadi aplikasi telepon default")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                // Pengguna menolak atau terjadi kesalahan
                Log.d("PermissionHelper", "Failed to set as default dialer")

                // Tampilkan dialog penjelasan
                AlertDialog.Builder(this)
                    .setTitle("Izin Diperlukan")
                    .setMessage("Aplikasi memerlukan izin untuk menjadi aplikasi telepon default. Mohon berikan izin untuk fungsionalitas penuh.")
                    .setPositiveButton(
                        "Coba Lagi"
                    ) { dialog: DialogInterface?, which: Int -> requestRolePermission() }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        }
    }

    companion object {
        private const val REQUEST_PHONE_ROLE = 1
        private const val REQUEST_CODE_CALL_PHONE = 1
    }
}