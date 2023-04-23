package algokelvin.app.conversion

import algokelvin.app.conversion.db.ConverterDatabase
import algokelvin.app.conversion.repository.ConverterDatabaseRepositoryImpl
import algokelvin.app.conversion.ui.base_screen.BaseScreen
import algokelvin.app.conversion.ui.theme.ConvertionTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = ConverterDatabase.getInstance(application).converterDao
        val repository = ConverterDatabaseRepositoryImpl(dao)
        val factory = ConversionViewModelFactory(repository)

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
