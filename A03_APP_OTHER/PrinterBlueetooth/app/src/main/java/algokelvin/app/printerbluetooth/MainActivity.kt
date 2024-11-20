package algokelvin.app.printerbluetooth

import algokelvin.app.printerbluetooth.databinding.ActivityMainBinding
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var mService: BluetoothService? = null
    private var mConnectedDeviceName: String? = null

    /** */ // Message types sent from the BluetoothService Handler
    companion object {
        private const val DEBUG = true
        const val DEVICE_NAME = "device_name"
        const val TOAST = "toast"
        const val MESSAGE_STATE_CHANGE = 1
        const val MESSAGE_READ = 2
        const val MESSAGE_WRITE = 3
        const val MESSAGE_DEVICE_NAME = 4
        const val MESSAGE_TOAST = 5
        const val MESSAGE_CONNECTION_LOST = 6
        const val MESSAGE_UNABLE_CONNECT = 7

        private const val TAG = "MainActivityDemoPrinter"
        private const val REQUEST_CONNECT_DEVICE = 1
        private const val REQUEST_ENABLE_BT = 2
        private const val REQUEST_CHOSE_BMP = 3
        private const val REQUEST_CAMER = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkingBluetooth()
        KeyListenerInit()
    }

    private fun checkingBluetooth() {
        mService = BluetoothService(applicationContext, mHandler)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Bluetooth is available", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()

        // If Bluetooth is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!bluetoothAdapter?.isEnabled!!) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, MainActivity.REQUEST_ENABLE_BT)
            // Otherwise, setup the session
        } /*else {
            if (mService == null) KeyListenerInit() //监听
        }*/
    }

    @Synchronized
    override fun onResume() {
        super.onResume()
        if (mService != null) {
            if (mService?.state === BluetoothService.STATE_NONE) {
                // Start the Bluetooth services
                mService?.start()
            }
        }
    }

    @Synchronized
    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the Bluetooth services
        mService?.stop()
    }

    private fun KeyListenerInit() {
        binding.btnService.setOnClickListener {
            Log.i(TAG, "Btn Service")
            val serverIntent = Intent(this, DeviceListActivity::class.java)
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE)
        }
        binding.btnPrint.setOnClickListener {
            Log.i(TAG, "Btn Print")
            BluetoothPrintTest()
        }
    }

    private fun BluetoothPrintTest() {
        val lang = getString(R.string.strLang)
        if (lang.compareTo("en") == 0) {
            SendDataString("--------------------------------")
            SendDataString("    Nama      qyt   Harga  Total")
            SendDataString("--------------------------------")
            SendDataString("Blueband       1    2000    2000")
            SendDataString("Pepsodent      2    9000   15000")
            SendDataString("Sasa           3    8000   14000")
            SendDataString("Bear Brand     4    7000   13000")
            SendDataString("Marimas        5    6000   12000")
            SendDataString("Markisa        7    5000    1000")
            SendDataString("Markicabs      8    4000    1000")
            SendDataString("Markicups      9    5000    1000")
            SendDataString("Marjan         10   10000   1000")
            SendDataString("--------  INI DATA DUMMY -------")
            SendDataString("Total                     10Juta")
            SendDataString("Bayar                     20Juta")
            SendDataString("Sisa                           0")
            SendDataString("--------------------------------")
            SendDataString("Terima Kasih")
            SendDataString("--------------------------------")
        }
    }

    private fun SendDataString(data: String) {
        if (mService!!.state !== BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (data.length > 0) {
            try {
                mService!!.write(data.toByteArray(charset("GBK")))
            } catch (e: UnsupportedEncodingException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
    }

    /** */
    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_STATE_CHANGE -> {
                    if (MainActivity.DEBUG) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1)
                    when (msg.arg1) {
                        BluetoothService.STATE_CONNECTED -> {
                            Log.i(TAG, "STATE_CONNECTED: " + msg.arg1)
                            /*mTitle.setText(R.string.title_connected_to)
                            mTitle.append(mConnectedDeviceName)
                            btnScanButton.setText(getText(R.string.Connecting))
                            btnScanButton.setEnabled(false)
                            testButton.setEnabled(true)*/
                        }
                        BluetoothService.STATE_CONNECTING -> {
                            Log.i(TAG, "STATE_CONNECTING: " + msg.arg1)
//                            mTitle.setText(R.string.title_connecting)
                        }
                        BluetoothService.STATE_LISTEN, BluetoothService.STATE_NONE -> {
                            Log.i(TAG, "STATE_LISTEN: " + msg.arg1)
//                            mTitle.setText(R.string.title_not_connected)
                        }
                    }
                }
                MESSAGE_WRITE -> {}
                MESSAGE_READ -> {}
                MESSAGE_DEVICE_NAME -> {
                    mConnectedDeviceName = msg.data.getString(DEVICE_NAME)
                    Toast.makeText(
                        applicationContext,
                        "Connected to $mConnectedDeviceName",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                MESSAGE_TOAST -> Toast.makeText(applicationContext, msg.data.getString(TOAST), Toast.LENGTH_SHORT).show()
                MESSAGE_CONNECTION_LOST -> {
                    Toast.makeText(applicationContext, "Device connection was lost", Toast.LENGTH_SHORT).show()
                    /*imageViewPicture.setEnabled(false)
                    sendButton.setEnabled(false)
                    testButton.setEnabled(false)*/
                }
                MESSAGE_UNABLE_CONNECT -> Toast.makeText(applicationContext, "Unable to connect device", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult $resultCode")
        when (requestCode) {
            REQUEST_CONNECT_DEVICE -> {
                // When DeviceListActivity returns with a device to connect
                if (resultCode == RESULT_OK) {
                    // Get the device MAC address
                    val address = data?.extras!!.getString(
                        DeviceListActivity.EXTRA_DEVICE_ADDRESS
                    )
                    // Get the BLuetoothDevice object
                    if (BluetoothAdapter.checkBluetoothAddress(address)) {
                        val device: BluetoothDevice = bluetoothAdapter?.getRemoteDevice(address)!!
                        // Attempt to connect to the device
                        mService!!.connect(device)
                    }
                }
            }
            REQUEST_ENABLE_BT -> {

                // When the request to enable Bluetooth returns
                if (resultCode == RESULT_OK) {
                    // Bluetooth is now enabled, so set up a session
                    KeyListenerInit()
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled")
                    Toast.makeText(
                        this, R.string.bt_not_enabled_leaving,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
            REQUEST_CHOSE_BMP -> {
                if (resultCode == RESULT_OK) {
                    val selectedImage = data?.data
                    val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA)
                    val cursor = contentResolver.query(
                        selectedImage!!,
                        filePathColumn, null, null, null
                    )
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    val picturePath = cursor.getString(columnIndex)
                    cursor.close()
                    val opts = BitmapFactory.Options()
                    opts.inJustDecodeBounds = true
                    BitmapFactory.decodeFile(picturePath, opts)
                    opts.inJustDecodeBounds = false
                    if (opts.outWidth > 1200) {
                        opts.inSampleSize = opts.outWidth / 1200
                    }
                    val bitmap = BitmapFactory.decodeFile(picturePath, opts)
                    if (null != bitmap) {
//                        imageViewPicture.setImageBitmap(bitmap)
                    }
                } else {
                    Toast.makeText(this, getString(R.string.msg_statev1), Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CAMER -> {
                if (resultCode == RESULT_OK) {
//                    handleSmallCameraPhoto(data);
                } else {
                    Toast.makeText(this, getText(R.string.camer), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}