@file:OptIn(ExperimentalComposeUiApi::class)

package com.helpyapps.unforgetty.screens.task.items.input

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onPreInterceptKeyBeforeSoftKeyboard
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    text: String,
    placeholderText: String = "",
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedColor: Color = MaterialTheme.colorScheme.secondary,
    singleLine: Boolean = false,
    minLines: Int = 1,
    expandable: Boolean = false,
    onDoneClick: () -> Unit = {},
    onHideClick: () -> Unit = {}
) {
    var textField by remember { mutableStateOf(TextFieldValue(text)) }
    var focused by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(true) }

    BasicTextField(
        modifier = Modifier
            .animateContentSize(animationSpec = tween(150, easing = LinearEasing))
            .onFocusChanged {
                if (it.isFocused) {
                    focused = true
                    expanded = true
                } else focused = false
            }
            .onSizeChanged {
                textField = if (expanded) textField.copy(selection = TextRange(textField.text.length))
                else textField.copy(selection = TextRange.Zero)
            }
            .onPreInterceptKeyBeforeSoftKeyboard { event ->
                if (event.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_BACK) {
                    onHideClick()
                    true
                } else false
            },
        value = textField,
        onValueChange = { textField = it },
        singleLine = singleLine,
        maxLines = if (expanded) Int.MAX_VALUE else minLines,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.primary,
            fontSize = fontSize
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDoneClick() }),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                CompositionLocalProvider(LocalContentColor provides(
                        if (textField.text.isEmpty()) unfocusedColor else focusedColor
                )) {
                    leadingIcon?.invoke()
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Box(modifier = modifier) {
                        if (textField.text.isEmpty()) {
                            Text(
                                text = placeholderText,
                                style = LocalTextStyle.current.copy(
                                    color = unfocusedColor,
                                    fontSize = fontSize
                                )
                            )
                        } else {
                            innerTextField()
                        }
                    }
                    HorizontalDivider(color = if (focused) focusedColor else unfocusedColor)
                }
                Column {
                    CompositionLocalProvider(LocalContentColor provides(
                            if (textField.text.isEmpty()) unfocusedColor else focusedColor
                    )) {
                        trailingIcon?.invoke()
                    }
                    if (expandable) {
                        Icon(
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                expanded = !expanded
                                onHideClick()
                                onDoneClick()
                            },
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    )
}