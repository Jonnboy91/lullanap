package com.jonnesten.lullanap.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jonnesten.lullanap.R

// Set of Material typography styles to start with
val Raleway = FontFamily(
    Font(R.font.raleway_light, FontWeight.W300),
    Font(R.font.raleway_light_italic, FontWeight.W300, FontStyle.Italic),
    Font(R.font.raleway_medium, FontWeight.W500),
    Font(R.font.raleway_medium_italic, FontWeight.W500, FontStyle.Italic),
    Font(R.font.raleway_semi_bold, FontWeight.W600),
    Font(R.font.raleway_semi_bold_italic, FontWeight.W600, FontStyle.Italic),
    Font(R.font.raleway_bold, FontWeight.W700),
    Font(R.font.raleway_bold_italic, FontWeight.W700, FontStyle.Italic),
    Font(R.font.raleway_extra_bold, FontWeight.W800),
    Font(R.font.raleway_extra_bold_italic, FontWeight.W800, FontStyle.Italic)
)
val typography = Typography(
    h1 = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W600,
        fontSize = 96.sp
    ),
    body1 = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W300,
        fontSize = 16.sp
    )
)