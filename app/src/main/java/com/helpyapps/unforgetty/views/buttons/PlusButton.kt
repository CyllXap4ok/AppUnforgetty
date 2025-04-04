package com.helpyapps.unforgetty.views.buttons

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlusButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val circleColor = MaterialTheme.colorScheme.primary
    val linesColor = MaterialTheme.colorScheme.onPrimary

    var selected by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (selected) 0.9f else 1f, label = "")

    Canvas(
        modifier
            .size(50.dp)
            .scale(scale)
            .pointerInteropFilter {
                when(it.action) {
                    MotionEvent.ACTION_UP -> {
                        selected = false
                        onClick()
                    }
                    MotionEvent.ACTION_DOWN -> selected = true
                }
                true
            }
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {}
    ) {
        drawCircle(color = circleColor)
        drawLine(
            color = linesColor,
            start = Offset(16f, this.size.height / 2),
            end = Offset(this.size.width - 16, this.size.height / 2),
            strokeWidth = 4f
        )
        drawLine(
            color = linesColor,
            start = Offset(this.size.width / 2, 16f),
            end = Offset(this.size.width / 2, this.size.height - 16),
            strokeWidth = 4f
        )
    }
}