package com.helpyapps.unforgetty.view.screen.task.items

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun PickInput(
    text: String = "",
    hintText: String = "",
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedColor: Color = MaterialTheme.colorScheme.secondary,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onPickClick: () -> Unit = {}
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        CompositionLocalProvider(LocalContentColor provides(
                if (text.isEmpty()) unfocusedColor else focusedColor)
        ) {
            leadingIcon?.invoke()
        }

        Column(
            modifier = Modifier.weight(1f).clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onPickClick()
            },
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = text.ifEmpty { hintText },
                fontSize = fontSize,
                color = if (text.isEmpty()) unfocusedColor else focusedColor)
            HorizontalDivider(color = unfocusedColor)
        }

        Column {
            CompositionLocalProvider(
                LocalContentColor provides(if (text.isEmpty()) unfocusedColor else focusedColor)
            ) {
                trailingIcon?.invoke()
            }
        }
    }
}