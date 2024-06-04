package algokelvin.app.downloadapps


interface InstalledView {
    fun isNeedInstall(app: App)
    fun isNeedUpdate(app: App)
    fun openApp()
}