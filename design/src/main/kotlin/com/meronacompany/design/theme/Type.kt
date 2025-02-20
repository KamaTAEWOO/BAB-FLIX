package com.meronacompany.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
* Text(
       text = "Hello, Roboto!",
       style = typography.displayLarge,
  )
* */

@Immutable
data class BobFlixTypography(
    val textStyleBold12: TextStyle,
    val textStyleBold14: TextStyle,
    val textStyleBold16: TextStyle,
    val textStyleBold18: TextStyle,
    val textStyleBold20: TextStyle,
    val textStyleBold24: TextStyle,
    val textStyleBold26: TextStyle,
    val textStyleBold30: TextStyle,
    val textStyleRegular10: TextStyle,
    val textStyleRegular12: TextStyle,
    val textStyleRegular14: TextStyle,
    val textStyleRegular16: TextStyle,
    val textStyleRegular18: TextStyle,
    val textStyleRegular20: TextStyle,
    val textStyleRegular24: TextStyle,
    val textStyleRegular26: TextStyle,
    val textStyleRegular30: TextStyle,
    val textStyleLight10: TextStyle,
    val textStyleLight12: TextStyle,
    val textStyleLight14: TextStyle,
    val textStyleLight16: TextStyle,
    val textStyleLight18: TextStyle,
    val textStyleLight20: TextStyle,
    val textStyleLight24: TextStyle,
    val textStyleLight30: TextStyle,
    val textStyleItalic12: TextStyle,
    val textStyleItalic14: TextStyle,
    val textStyleItalic16: TextStyle,
    val textStyleItalic18: TextStyle,
    val textStyleItalic20: TextStyle,
    val textStyleItalic24: TextStyle,
    val textStyleItalic30: TextStyle,
    val textStyleMedium12: TextStyle,
    val textStyleMedium14: TextStyle,
    val textStyleMedium16: TextStyle,
    val textStyleMedium18: TextStyle,
    val textStyleMedium20: TextStyle,
    val textStyleMedium22: TextStyle,
    val textStyleMedium24: TextStyle,
    val textStyleMedium26: TextStyle,
    val textStyleMedium28: TextStyle,
    val textStyleMedium30: TextStyle
)

val LocalBabFlixTypography = staticCompositionLocalOf {
    BobFlixTypography(
        textStyleBold12 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 12.dp.value.sp
        ),
        textStyleBold14 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 14.dp.value.sp
        ),
        textStyleBold16 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 16.dp.value.sp
        ),
        textStyleBold18 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 18.dp.value.sp
        ),
        textStyleBold20 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 20.dp.value.sp
        ),
        textStyleBold24 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 24.dp.value.sp
        ),
        textStyleBold26 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 26.dp.value.sp
        ),
        textStyleBold30 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 30.dp.value.sp
        ),
        textStyleRegular10 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 10.dp.value.sp
        ),
        textStyleRegular12 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 12.dp.value.sp
        ),
        textStyleRegular14 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 14.dp.value.sp
        ),
        textStyleRegular16 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 16.dp.value.sp
        ),
        textStyleRegular18 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 18.dp.value.sp
        ),
        textStyleRegular20 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 20.dp.value.sp
        ),
        textStyleRegular24 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 24.dp.value.sp
        ),
        textStyleRegular26 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 26.dp.value.sp
        ),
        textStyleRegular30 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 30.dp.value.sp
        ),
        textStyleLight10 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Light,
            fontSize = 10.dp.value.sp
        ),
        textStyleLight12 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Light,
            fontSize = 12.dp.value.sp
        ),
        textStyleLight14 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Light,
            fontSize = 14.dp.value.sp
        ),
        textStyleLight16 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Light,
            fontSize = 16.dp.value.sp
        ),
        textStyleLight18 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Light,
            fontSize = 18.dp.value.sp
        ),
        textStyleLight20 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Light,
            fontSize = 20.dp.value.sp
        ),
        textStyleLight24 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Light,
            fontSize = 24.dp.value.sp
        ),
        textStyleLight30 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Light,
            fontSize = 30.dp.value.sp
        ),
        textStyleItalic12 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 12.dp.value.sp
        ),
        textStyleItalic14 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 14.dp.value.sp
        ),
        textStyleItalic16 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 16.dp.value.sp
        ),
        textStyleItalic18 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 18.dp.value.sp
        ),
        textStyleItalic20 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 20.dp.value.sp
        ),
        textStyleItalic24 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 24.dp.value.sp
        ),
        textStyleItalic30 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 30.dp.value.sp
        ),
        textStyleMedium12 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 12.dp.value.sp
        ),
        textStyleMedium14 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 14.dp.value.sp
        ),
        textStyleMedium16 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 16.dp.value.sp
        ),
        textStyleMedium18 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 18.dp.value.sp
        ),
        textStyleMedium20 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 20.dp.value.sp
        ),
        textStyleMedium22 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 22.dp.value.sp
        ),
        textStyleMedium24 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 24.dp.value.sp
        ),
        textStyleMedium26 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 26.dp.value.sp
        ),
        textStyleMedium28 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 28.dp.value.sp
        ),
        textStyleMedium30 = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 30.dp.value.sp
        )
    )
}
