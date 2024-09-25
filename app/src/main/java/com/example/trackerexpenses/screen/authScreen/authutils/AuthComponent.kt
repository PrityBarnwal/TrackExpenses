package com.example.trackerexpenses.screen.authScreen.authutils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp


@Composable
fun CommonOutlinedTextFieldAuth(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier,
    labelText: String = "",
    keyboardType: KeyboardType, imeAction: ImeAction
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        isError = isError,
        label = { Text(labelText, color = Color.White) },
        singleLine = true,
        textStyle =
        TextStyle(color = Color.White, fontSize = 12.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            cursorColor = Color.White,
            textColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ), modifier = modifier.fillMaxWidth()
    )
}