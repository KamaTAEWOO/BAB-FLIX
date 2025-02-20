package com.meronacompany.bab_flix.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.meronacompany.bab_flix.R

// Define Roboto FontFamily with different weights and styles
val roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.roboto_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.roboto_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic)
)
