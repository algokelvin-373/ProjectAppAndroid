package algokelvin.app.downloadapps

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.*
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.internal.ViewUtils.dpToPx
import com.google.android.material.snackbar.Snackbar
import com.tsm.aptoide.services.DownloadServices
import com.tsm.aptoide.services.StopDownloadServices
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_app_overview.*

class AppDownloadActivity : AppCompatActivity(), InstalledView {

    private val TAG = this::class.java.simpleName + TSMLOG
    private val disposable = CompositeDisposable()
    private var colorTheme = R.color.appsAccent
    private lateinit var app: App

    private val appOverViewViewModel by lazy { AppVMInjector.appOverViewViewModel(this) }

    private val broadcastDownloading = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val identifier = intent.getStringExtra("identifier")

            if (identifier == app.packageName) {
                val pos = intent.getIntExtra("progress_state", 0)
                val max = intent.getIntExtra("progress_max", 0)
                val isDownload = intent.getBooleanExtra("is_download", true)

                btn_install.visibility = View.GONE
                container_progress.visibility = View.VISIBLE
                progress_installing.isIndeterminate = false
                progress_installing.max = max
                progress_installing.progress = pos
                text_progress_installing.text = getFileSize(pos.toLong())

                btn_cancel_download.visibility = View.VISIBLE

                btn_cancel_download.setOnClickListener {
                    startStopService()
                }

                if (pos == max) {
                    startStopService()
                }

                if (!isDownload) {
                    startStopService()
                }


                logi("progress is --> $pos")
            }

            if (!isDownloadRunning(this@AppDownloadActivity)) {
                btn_install.isEnabled = true
                btn_install.visibility = View.VISIBLE
                btn_install.invalidate()
                container_progress.visibility = View.GONE
            }
        }
    }

    private val broadcastUpdateState = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {

            val updateView = intent.getBooleanExtra("update_view", false)

            if (updateView) {
                InstalledChecker(this@AppDownloadActivity, app).check(
                    app.packageName,
                    app.file.vercode
                )
            }

            startStopService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_overview)
        initColor(resources.getColor(R.color.appsAccent))

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*if (!dir().exists()) {
            dir().mkdir()
        }

        val packageName = intent.getStringExtra("package_name")
        Log.i("app-detail pack", packageName.toString())
        if (packageName != null) {
            appOverViewViewModel.getAppOverView(this, packageName).observe(this, Observer {
                it?.let { appData ->
                    app = appData
                    Log.i("app-detail pack", app.toString())
                    //appData.media.screenshots
                    parent_layout.visibility = View.VISIBLE
                    progress_circular.visibility = View.GONE
                    checkInstalledAndUpdate(appData)
                    setupView(appData)
                }
            })

            appOverViewViewModel.getGroupOverView(this, packageName).observe(this, Observer {
                setupGroup(it?.id)
            })
        }*/
    }

    private fun setupGroup(id: Long?) {
        val adapterGroup = AppCategoryPagedAdapter()
        similar_app.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        similar_app.adapter = adapterGroup
        val appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        appViewModel.getApps(this, id!!, 8).observe(this, Observer {
            adapterGroup.submitList(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setupView(app: App?) {
        name_app.text = app?.name
        publisher_app.text = app?.developer?.name
        version_app.text = "Ver. ${app?.file?.vername}"
//        size_app.text = longToSize(app?.size)
        desc_app_full.text = app?.media?.description
        new_in_app.text = app?.media?.new

        if (new_in_app.text.isEmpty()) new_container.visibility = View.GONE
        if (desc_app_full.text.isEmpty()) {
            desc_app_full.visibility = View.GONE
            btn_more.visibility = View.GONE
        }

//        Glide.with(this)
//            .asBitmap()
//            .load(app?.icon)
//            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//            .addListener(object : RequestListener<Bitmap> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Bitmap>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    model: Any?,
//                    target: Target<Bitmap>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    disposable.add(
//                        Completable.fromAction {
//                            Palette.from(resource)
//                                .generate {
//                                    it?.let { palette ->
//                                        val swatch = palette.vibrantSwatch
//                                        val rgb = swatch?.rgb
//                                        if (rgb != null) initColor(rgb)
//                                    }
//                                }
//                        }.subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe()
//                    )
//                    return false
//                }
//
//            }).into(icon_app)

        img_list_screenshot.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        /*img_list_screenshot.adapter = app?.media?.let { ScreenshotAdapter(it) }
        GravitySnapHelper(Gravity.START).attachToRecyclerView(img_list_screenshot)*/

        registerReceiver(broadcastDownloading, IntentFilter("download"))
        registerReceiver(broadcastUpdateState, IntentFilter("update_view"))

        btn_more.setOnClickListener {
            intentDesc(app, 0)
        }
        btn_more_new.setOnClickListener {
            intentDesc(app, 1)
        }
    }

    private fun intentDesc(app: App?, focus: Int) {
        val intent = Intent(this, AppDescActivity::class.java)
        val bundle = Bundle()
        bundle.putString("app_name", app?.name)
        bundle.putString("app_desc", app?.media?.description)
        bundle.putString("app_new", app?.media?.new)
        bundle.putInt("app_color", colorTheme)
        bundle.putInt("new_focus", focus)
        intent.putExtra("app", bundle)
        startActivity(intent)
    }

    private fun initColor(color: Int) {
        colorTheme = color

//        ViewUtil.setColorStatusbar(this, color)

        appbar_main.setBackgroundColor(color)

        btn_install.backgroundTintList = ColorStateList.valueOf(color)
        btn_cancel_download.backgroundTintList = ColorStateList.valueOf(color)

        btn_more.setTextColor(color)
        btn_more_new.setTextColor(color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progress_installing.progressTintList = ColorStateList.valueOf(color)
        }
    }

    private fun checkInstalledAndUpdate(app: App) {

        disposable.add(
            Completable.fromAction {
                InstalledChecker(this, app).check(app.packageName, app.file.vercode)
            }
                .subscribeOn(Schedulers.trampoline())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun isNeedInstall(app: App) {
        Log.i("app log", "Process Install")
        btn_install.text = "Install"
        btnInstallClicked(app)
    }

    override fun isNeedUpdate(app: App) {
        btn_install.text = "Update"
        btnInstallClicked(app)
    }

    override fun openApp() {
        btn_install.text = "Open"
        btn_install.setOnClickListener {
            Log.i("app-detail apppack", app.packageName)
            /*val launchIntent = packageManager.getLaunchIntentForPackage(app.packageName)
            if (launchIntent != null) {
                startActivity(launchIntent)//null pointer check in case package name was not found
            }*/
        }
    }

    private fun btnInstallClicked(app: App) {
        btn_install.setOnClickListener {
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
        val urlNewApk = app.file.path
        val newFileName = "${app.packageName}_${app.file.vername}.apk"
        Log.i("app log", "Start Download")
        Log.i("app-detail url", app.file.toString())
        Log.i("app-detail file", newFileName)
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

    private fun startStopService() {

        btn_install.isEnabled = true
        btn_install.visibility = View.VISIBLE
        btn_install.invalidate()
        container_progress.visibility = View.GONE

        val intent = Intent(this, StopDownloadServices::class.java)
        intent.putExtra("version_code", app.file.vercode)
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        try {
            unregisterReceiver(broadcastDownloading)
            unregisterReceiver(broadcastUpdateState)
        } catch (e: Exception) {
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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