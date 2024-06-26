package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.convertcurrency.ConvertCurrencyViewModel
import com.nicoqueijo.android.convertcurrency.R
import com.nicoqueijo.android.convertcurrency.composables.util.NumberPadState
import com.nicoqueijo.android.convertcurrency.model.UiEvent
import com.nicoqueijo.android.convertcurrency.model.UiState
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.S
import com.nicoqueijo.android.ui.XL
import com.nicoqueijo.android.ui.XXXS
import java.math.BigDecimal
import java.util.Locale

@Composable
fun ConvertCurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: ConvertCurrencyViewModel? = hiltViewModel(),
    onFabClick: (() -> Unit)? = null,
) {
    val uiState = viewModel?.uiState?.collectAsStateWithLifecycle()?.value
    ConvertCurrency(
        modifier = modifier,
        state = uiState,
        onFabClick = onFabClick,
        onEvent = { event ->
            viewModel?.onEvent(event = event)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertCurrency(
    modifier: Modifier = Modifier,
    state: UiState?,
    onFabClick: (() -> Unit)? = null,
    onEvent: ((UiEvent) -> Unit)? = null,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        navigationIcon = {
                            Row {
                                Spacer(modifier = Modifier.width(XXXS))
                                Image(
                                    modifier = Modifier.size(size = XL),
                                    painter = painterResource(id = R.drawable.app_icon),
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Text(
                                modifier = Modifier.padding(start = XXXS),
                                text = stringResource(id = R.string.app_name),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        },
                        actions = {
                            if (state?.currencies?.isNotEmpty() == true) {
                                IconButton(
                                    onClick = {
                                        onEvent?.invoke(UiEvent.UnselectAllCurrencies)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    HorizontalDivider()
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(paddingValues = innerPadding),
            ) {
                if (state?.showDialog == true) {
                    RemoveCurrenciesDialog(
                        onConfirmClick = {
                            onEvent?.invoke(UiEvent.ConfirmDialog)
                        },
                        onDismissClick = {
                            onEvent?.invoke(UiEvent.CancelDialog)
                        },
                    )
                }
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        if (state?.currencies?.isEmpty() == true) {
                            EmptyListIndicator()
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                state?.currencies?.forEach { currency ->
                                    item {
                                        ConvertCurrencyRow(
                                            modifier = Modifier.animateItem(),
                                            state = currency,
                                            onClick = {
                                                onEvent?.invoke(
                                                    UiEvent.SetCurrencyFocus(currency = currency)
                                                )
                                            },
                                        )
                                        HorizontalDivider()
                                    }
                                }
                                item {
                                    // Ensures the Floating Action Button (FAB) does not obscure the last item when the list is scrolled to its bottommost position.
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(88.dp)
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .height(72.dp)
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.surface,
                                        )
                                    )
                                )
                        )
                        FloatingActionButton(
                            modifier = Modifier.padding(bottom = S),
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.secondary,
                            onClick = { onFabClick?.invoke() },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                            )
                        }
                    }
                    Box {
                        NumberPad(
                            state = NumberPadState(
                                locale = Locale.getDefault(),
                                onKeyboardButtonClick = { keyboardInput ->
                                    onEvent?.invoke(
                                        UiEvent.ProcessKeyboardInput(keyboardInput = keyboardInput)
                                    )
                                },
                                onKeyboardButtonLongClick = { keyboardInput ->
                                    UiEvent.ProcessKeyboardInput(keyboardInput = keyboardInput)
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenPreview() {
    val state = UiState(
        currencies = listOf(
            Currency(
                currencyCode = "USD_GBP",
                exchangeRate = 0.786829,
                position = 0,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("22.4246265")
            },
            Currency(
                currencyCode = "USD_USD",
                exchangeRate = 1.0,
                position = 1,
                isSelected = true,
            ).apply {
                isFocused = true
                conversion.value = BigDecimal("28.5")
            },
            Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 0.931032,
                position = 2,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("26.534412")
            },
            Currency(
                currencyCode = "USD_JPY",
                exchangeRate = 157.837,
                position = 3,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("4498.3545")
            },
        )
    )
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyEmptyScreenPreview() {
    val state = UiState()
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenWithDialogPreview() {
    val state = UiState(
        currencies = listOf(
            Currency(
                currencyCode = "USD_GBP",
                exchangeRate = 0.786829,
                position = 0,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("22.4246265")
            },
            Currency(
                currencyCode = "USD_USD",
                exchangeRate = 1.0,
                position = 1,
                isSelected = true,
            ).apply {
                isFocused = true
                conversion.value = BigDecimal("28.5")
            },
            Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 0.931032,
                position = 2,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("26.534412")
            },
            Currency(
                currencyCode = "USD_JPY",
                exchangeRate = 157.837,
                position = 3,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("4498.3545")
            },
        ),
        showDialog = true,
    )
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenManyCurrenciesPreview() {
    val state = UiState(
        currencies = listOf(
            Currency(
                currencyCode = "USD_GBP",
                exchangeRate = 0.786829,
                position = 0,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("22.4246265")
            },
            Currency(
                currencyCode = "USD_USD",
                exchangeRate = 1.0,
                position = 1,
                isSelected = true,
            ).apply {
                isFocused = true
                conversion.value = BigDecimal("28.5")
            },
            Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 0.931032,
                position = 2,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("26.534412")
            },
            Currency(
                currencyCode = "USD_JPY",
                exchangeRate = 157.837,
                position = 3,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("4498.3545")
            },
            Currency(
                currencyCode = "USD_CHF",
                exchangeRate = 0.898383,
                position = 4,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("25.1547")
            },
            Currency(
                currencyCode = "USD_CNY",
                exchangeRate = 7.2673,
                position = 5,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("203.4844")
            },
            Currency(
                currencyCode = "USD_KRW",
                exchangeRate = 1381.270188,
                position = 6,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("38675.5653")
            },
            Currency(
                currencyCode = "USD_CAD",
                exchangeRate = 1.36734,
                position = 7,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("38.2855")
            },
            Currency(
                currencyCode = "USD_RUB",
                exchangeRate = 85.500917,
                position = 8,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("2394.0257")
            },
            Currency(
                currencyCode = "USD_HKD",
                exchangeRate = 7.808621,
                position = 9,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("218.6414")
            },
        )
    )
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}