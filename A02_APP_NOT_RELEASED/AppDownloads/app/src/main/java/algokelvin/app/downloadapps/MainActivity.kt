package algokelvin.app.downloadapps

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.tsm.aptoide.services.DownloadServices
import kotlinx.android.synthetic.main.activity_app_overview.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_install_apk.setOnClickListener {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
                val alert = AlertDialog.Builder(this)
                alert.setTitle(getString(R.string.info_install))
                alert.setPositiveButton(getString(R.string.ok)) { _, _ ->
                    downloadApk(app)
                }
                alert.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
                alert.show()
            } else{
                downloadApk(app)
            }
        }

    }

    private fun downloadApk(app: App) {
        val urlNewApk = "https://pool.apk.aptoide.com/catappult/tamer-android-qrcode-2-53089106-2c8908ee6f1fd8375d63e50c45bfba1c.apk"
        val newFileName = "tamer.android.qrcode_1.1.apk"
        Log.i("app log", "Start Download")
        if (isDownloadRunning(this)) {
            snackbar(
                this,
                parent_layout,
                "Wait for other download installing",
                R.drawable.ic_warning
            )
            Log.i("app log", "Download Running")
        } else {
            Log.i("app log", "download not run, start download service")
            progress_installing.isIndeterminate = true
            btn_install.visibility = View.GONE
            container_progress.visibility = View.VISIBLE
            btn_cancel_download.visibility = View.GONE
            text_progress_installing.text = "Prepare downloading.."

            val intent = Intent(this, DownloadServices::class.java)
            intent.putExtra("url", urlNewApk)
            intent.putExtra("file_name", newFileName)
            intent.putExtra("package_name", app.packageName)
            intent.putExtra("name_app", app.name)
            intent.putExtra("image", app.icon)
            intent.putExtra("version_code", app.file.vercode)
            intent.putExtra("app_name", app.name)
            startService(intent)
        }
    }

    private fun isDownloadRunning(context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Integer.MAX_VALUE)) {
            if (DownloadServices().javaClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun snackbar(appCompatActivity: AppCompatActivity, layout: View, msg: String, icon: Int) {
        val snackbar = Snackbar.make(layout, msg, Snackbar.LENGTH_LONG)
        val snackbarLayout = snackbar.view
        //val textView = snackbarLayout.findViewById(android.support.design.R.id.snackbar_text) as TextView
        val textView = snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        //textView.compoundDrawablePadding = ConvertUtils.px2dp(12f)
//        textView.compoundDrawablePadding = dpToPx(12f, appCompatActivity.resources).toInt()
        textView.gravity = Gravity.CENTER
        snackbar.show()
    }
}