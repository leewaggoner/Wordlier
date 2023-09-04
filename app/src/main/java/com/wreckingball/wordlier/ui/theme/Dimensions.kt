package com.wreckingball.wordlier.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimensions(
    val GuessLetterBoxDims: Dp = 50.dp,
    val KeyboardLetterBoxDims: Dp = 32.dp,
    val ActionBoxWidth: Dp = 42.dp,
    val BackIconWidth: Dp = 32.dp,
    val BackIconHeight: Dp = 22.dp,
    val LetterBoxCornerDims: Dp = 8.dp,
    val LetterBoxBorderDims: Dp = 1.dp,
    val KeyboardRowPaddingDims: Dp = 8.dp,
    val KeyboardTextPaddingDims: Dp = 4.dp,
    val LetterVerticalPadding: Dp = 4.dp,
    val LetterHorizontalPadding: Dp = 8.dp,
    val GuessRowPaddingDims: Dp = 8.dp,
    val GameBoardPadding: Dp = 32.dp,
    val ButtonWidth: Dp = 160.dp,
    val ButtonHeight: Dp = 40.dp,
    val IntroPadding: Dp = 32.dp,
    val IntroIconSize: Dp = 60.dp,
    val ExampleHeight: Dp = 40.dp,

    val EnterTextSize: TextUnit = 11.sp,
    val KeyboardTextSize: TextUnit = 22.sp,
    val GuessTextSize: TextUnit = 36.sp,
    val IntroTitleSize: TextUnit = 30.sp,
    val IntroDescriptionSize: TextUnit = 24.sp,
    val IntroNormalTextSize: TextUnit = 18.sp,

    val NormalSpace: Dp = 8.dp,
    val BiggerSpace: Dp = 16.dp,
    val IntroBigSpace: Dp = 48.dp,
)
