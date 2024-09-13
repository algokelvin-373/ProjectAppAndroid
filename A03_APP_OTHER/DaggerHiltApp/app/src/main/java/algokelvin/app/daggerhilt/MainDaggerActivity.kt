package algokelvin.app.daggerhilt

import algokelvin.app.daggerhilt.using_dagger.App
import algokelvin.app.daggerhilt.using_dagger.DataSource
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import javax.inject.Inject

class MainDaggerActivity : AppCompatActivity() {

    @Inject
    lateinit var dataSource: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // For Dagger 2
        (application as App).dataComponent.inject(this)
        dataSource.getRemoteData()

    }
}