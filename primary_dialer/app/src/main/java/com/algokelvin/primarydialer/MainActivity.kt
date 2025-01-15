package com.algokelvin.primarydialer

import android.content.Context
import android.content.Intent
import android.media.audiofx.NoiseSuppressor
import android.net.Uri
import android.os.Bundle
import android.telecom.TelecomManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.primarydialer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val TAG = "PermissionHelper"
    private val REQUEST_PHONE_ROLE: Int = 2
    private lateinit var binding: ActivityMainBinding
    private lateinit var noiseSuppressor: NoiseSuppressor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnActiveDialer.setOnClickListener {
            requestRolePermission()
        }

        /*requestSetDefaultDialer(this)

        binding.callButton.setOnClickListener {
            val phoneNumber = binding.phoneNumberInput.text.toString()
            if (phoneNumber.isNotEmpty() &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
            ) {
                enableAudioEffect()
                makePhoneCall(phoneNumber)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            }
        }*/
    }

    private fun requestRolePermission() {
        val permissionHelper = PermissionHelper()
        permissionHelper.setPermission(this)
    }

    private fun requestSetDefaultDialer(context: Context) {
        val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        val packageName = context.packageName

        val defaultDialerPackage = telecomManager.defaultDialerPackage
        if (defaultDialerPackage != packageName) {
            val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
            intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Aplikasi ini sudah menjadi dialer default", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enableAudioEffect() {
        enableNoiseSuppressor()
    }

    private fun enableNoiseSuppressor() {
        if (NoiseSuppressor.isAvailable()) {
            try {
                val dummySessionId = 0
                noiseSuppressor = NoiseSuppressor.create(dummySessionId)
                noiseSuppressor.enabled = true
                Log.i(TAG, "Success for active noise suppressor")
            } catch (e: Exception) {
                Log.i(TAG, "Failed for active noise suppressor")
            }
        } else {
            Log.i(TAG, "NoiseSuppressor not supported on this device")
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
//        val intent = Intent(this, InCallActivity::class.java)
//        intent.putExtra("phone_number", phoneNumber)
//        startActivity(intent)

        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            binding.callButton.performClick()
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("PermissionHelper", "11 Successfully set as default dialer")
        Log.d("PermissionHelper", "RequestCode: $requestCode")
        Log.d("PermissionHelper", "resultCode: $resultCode")
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("PermissionHelper", "11 Successfully set as default dialer")
        Log.d("PermissionHelper", "RequestCode: $requestCode")
        Log.d("PermissionHelper", "resultCode: $resultCode")

        *//*if (requestCode == REQUEST_PHONE_ROLE) {
            if (resultCode == RESULT_OK) {
                // Berhasil menjadi aplikasi telepon default
                Log.d("PhoneRole", "Successfully set as default dialer")

                // Tambahkan dialog atau toast untuk memberi tahu pengguna
                AlertDialog.Builder(this)
                    .setTitle("Aplikasi Telepon")
                    .setMessage("Aplikasi ini sekarang menjadi aplikasi telepon default")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                // Pengguna menolak atau terjadi kesalahan
                Log.d("PhoneRole", "Failed to set as default dialer")

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
        }*//*
    }*/
}