package algokelvin.app.conversion.ui.converter

import algokelvin.app.conversion.compose.base.BaseScreen
import algokelvin.app.conversion.ui.theme.ConvertionTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Using Dagger Hilt
    @Inject
    lateinit var factory: ConversionViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Not use Dagger Hilt
        /*val dao = ConverterDatabase.getInstance(application).converterDao
        val repository = ConverterDatabaseRepositoryImpl(dao)
        val factory = ConversionViewModelFactory(repository)*/

        setContent {
            ConvertionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BaseScreen(factory = factory)
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ConversionActivityPreview() {
    BaseScreen()
}*/
