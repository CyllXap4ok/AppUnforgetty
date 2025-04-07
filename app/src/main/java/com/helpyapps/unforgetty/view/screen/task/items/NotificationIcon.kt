package com.helpyapps.unforgetty.view.screen.task.items

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun NotificationIcon(
    iconResourceID: Int,
    focusRequester: FocusRequester,
    onClick: () -> Unit = {},
) {

    val primaryColor = MaterialTheme.colorScheme.primary
    var borderColor by remember { mutableStateOf(Color.Transparent) }

    Box(
        modifier = Modifier
            .border(2.dp, color = borderColor, shape = RoundedCornerShape(10))
            .focusRequester(focusRequester)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusRequester.requestFocus()
                onClick()
            }
            .onFocusChanged {
                borderColor = if (it.isFocused) primaryColor else Color.Transparent
            }
            .focusable(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(iconResourceID),
            modifier = Modifier.padding(5.dp),
            contentDescription = ""
        )
    }
}