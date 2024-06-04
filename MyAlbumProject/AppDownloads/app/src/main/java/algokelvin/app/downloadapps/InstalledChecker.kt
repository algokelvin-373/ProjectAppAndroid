package algokelvin.app.downloadapps

import android.content.Context
import com.tsm.aptoide.data.App
import com.tsm.base.isAppInstalled

class InstalledChecker(private val context: Context, private val app: App) {

    private val view = context as InstalledView

    fun check(packageApp: String, vercode: Long) {

        if (isAppInstalled(context, packageApp)) {
            view.openApp()

            val pack = context.packageManager.getPackageInfo(packageApp, 0).versionCode
            if (vercode > pack) {
                view.isNeedUpdate(app)
            }
        } else {
            view.isNeedInstall(app)
        }
    }


}