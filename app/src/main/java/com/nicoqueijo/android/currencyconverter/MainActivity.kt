package com.nicoqueijo.android.currencyconverter

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.currencyconverter.error.ErrorScreen
import com.nicoqueijo.android.currencyconverter.ui.theme.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.data.CurrencyRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var result = "Uninitialized"

        runBlocking {
            currencyRepository.getExchangeRates().onSuccess {
                result = it.exchangeRates!!.currencies.toString()
            }.onFailure {
                result = it.message.toString()
            }
        }

        setContent {
            AndroidCurrencyConverterTheme {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun Greeting(message: String, modifier: Modifier = Modifier) {
    Text(text = message)
}