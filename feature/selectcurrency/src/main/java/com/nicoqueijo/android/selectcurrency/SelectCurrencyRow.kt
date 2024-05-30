package com.nicoqueijo.android.selectcurrency

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.Green
import com.nicoqueijo.android.ui.extensions.getDrawableResourceByName
import com.nicoqueijo.android.ui.extensions.getStringResourceByName

@Composable
fun SelectCurrencyRow(
    modifier: Modifier = Modifier,
    state: Currency,
) {
    Row(
        modifier = modifier
            .background(color = Color.White) // TODO: should be black/white according to the theme
            .fillMaxWidth()
            .height(64.dp)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clip(shape = RoundedCornerShape(2.dp)),
            contentDescription = null,
            painter = painterResource(
                id = LocalContext.current.getDrawableResourceByName(name = state.currencyCode.lowercase())
            )
        )

        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.trimmedCurrencyCode,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = LocalContext.current.getStringResourceByName(name = state.currencyCode),
                fontSize = 18.sp,
                maxLines = 1,
            )
        }
        if (state.isSelected) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                tint = Green, // TODO: Ensure this looks good on dark/light themes
            )
        }
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyRowPreview() {
    AndroidCurrencyConverterTheme {
        val currency = Currency(
            currencyCode = "USD_USD",
            exchangeRate = 1.0,
            isSelected = true,
        )
        Column {
            SelectCurrencyRow(state = currency)
        }
    }
}