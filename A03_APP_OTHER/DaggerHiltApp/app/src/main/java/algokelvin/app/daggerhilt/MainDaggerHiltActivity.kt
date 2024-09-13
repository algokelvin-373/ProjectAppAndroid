package algokelvin.app.daggerhilt

import algokelvin.app.daggerhilt.using_dagger.DataSource
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainDaggerHiltActivity : AppCompatActivity() {

    @Inject
    lateinit var dataSource: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // For Dagger Hilt --> Not need define inject component
        dataSource.getRemoteData()

    }
}