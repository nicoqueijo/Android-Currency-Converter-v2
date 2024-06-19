package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nicoqueijo.android.convertcurrency.R

@Composable
fun RemoveCurrenciesDialog(
    modifier: Modifier = Modifier,
    onConfirmClick: (() -> Unit)? = null,
    onDismissClick: (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = modifier,
        text = {
            Text(
                text = stringResource(id = R.string.remove_currencies_dialog_title)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick?.invoke()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.remove_currencies_dialog_confirm_label)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissClick?.invoke()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.remove_currencies_dialog_dismiss_label)
                )
            }
        },
        onDismissRequest = {
            onDismissClick?.invoke()
        },
    )
}