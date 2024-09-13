package algokelvin.app.downloadapps

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tsm.aptoide.data.App
import com.tsm.aptoide.datafactory.AppSourceFactory
import com.tsm.aptoide.datasource.AppDataSource
import com.tsm.base.*
import com.utsman.recycling.paged.extentions.NetworkState

class AppViewModel(application: Application) : BaseAndroidViewModel(application) {

    private var appSourceFactory: AppSourceFactory? = null

    fun getApps(context: Context?, group_id: Long, limit: Int) : LiveData<PagedList<App>> {
        appSourceFactory = AppSourceFactory(groupId = group_id, disposable = disposable, type = APPVIEW_CATEGORY, lang = getDefaultLang(context), limit = limit)
        return LivePagedListBuilder(appSourceFactory!!, configPaged(4)).build()
    }

    fun getAppsMore(context: Context?, group_id: Long, limit: Int): LiveData<PagedList<App>> {
        appSourceFactory = AppSourceFactory(groupId = group_id, disposable = disposable, type = APPVIEW_ALL, lang = getDefaultLang(context), limit = limit)
        return LivePagedListBuilder(appSourceFactory!!, configPaged(3)).build()
    }

    fun getLoader(): LiveData<NetworkState> = Transformations.switchMap<AppDataSource, NetworkState>(
            appSourceFactory?.app!!
    ) { it.networkState }
}