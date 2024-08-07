package com.nicoqueijo.android.selectcurrency.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.Green
import com.nicoqueijo.android.ui.L
import com.nicoqueijo.android.ui.XS
import com.nicoqueijo.android.ui.XXL
import com.nicoqueijo.android.ui.XXS
import com.nicoqueijo.android.ui.XXXS
import com.nicoqueijo.android.ui.XXXXS
import com.nicoqueijo.android.ui.extensions.getDrawableResourceByName
import com.nicoqueijo.android.ui.extensions.getStringResourceByName

/**
 * A composable function that displays a row representing a selectable currency.
 *
 * This function shows the currency's flag, code, and name in a horizontal row. If the currency is selected,
 * a checkmark icon is displayed on the right. The row is clickable, and clicking it triggers an optional
 * callback if the currency is not already selected.
 *
 * @param modifier The [Modifier] to be applied to this composable.
 * @param state The [Currency] state to be displayed in the row.
 * @param onClick An optional lambda to be invoked when the row is clicked, if the currency is not selected.
 */
@Composable
fun SelectCurrencyRow(
    modifier: Modifier = Modifier,
    state: Currency,
    onClick: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier.clickable {
            if (!state.isSelected) {
                onClick?.invoke()
            }
        }
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .height(height = XXL)
                .padding(
                    horizontal = XS,
                    vertical = XXS,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = XXXS)
                    .clip(
                        shape = RoundedCornerShape(size = XXXXS)
                    ),
                contentDescription = null,
                painter = painterResource(
                    id = LocalContext.current.getDrawableResourceByName(
                        name = state.currencyCode.lowercase()
                    )
                )
            )
            Spacer(
                modifier = Modifier.width(width = XS)
            )
            Column(
                modifier = Modifier.weight(weight = 1f)
            ) {
                Text(
                    modifier = Modifier.weight(weight = 1f),
                    text = state.trimmedCurrencyCode,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    modifier = Modifier.weight(weight = 1f),
                    text = LocalContext.current.getStringResourceByName(name = state.currencyCode),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    maxLines = 1,
                )
            }
            if (state.isSelected) {
                Icon(
                    modifier = Modifier.size(size = L),
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    tint = Green,
                )
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyRowPreview() {
    val currency = Currency(
        currencyCode = "USD_USD",
        exchangeRate = 1.0,
        isSelected = true,
    )
    AndroidCurrencyConverterTheme {
        SelectCurrencyRow(state = currency)
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyRowUnselectedPreview() {
    val currency = Currency(
        currencyCode = "USD_USD",
        exchangeRate = 1.0,
        isSelected = false,
    )
    AndroidCurrencyConverterTheme {
        SelectCurrencyRow(state = currency)
    }
}
