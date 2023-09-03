package com.wreckingball.wordlier.ui.howto

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

class HowToViewModel : ViewModel() {
    fun buildRulesString(strList: List<String>) : AnnotatedString {
        val bullet = "\u2022"
        val bulletStyle = ParagraphStyle(textIndent = TextIndent(restLine = 16.sp))
        val rules = buildAnnotatedString {
            strList.forEach { line ->
                withStyle(style = bulletStyle) {
                    append(bullet)
                    append("\t\t")
                    append(line)
                }
            }
        }

        return rules
    }

    fun buildExampleString(letter: String, exampleStr: String) : AnnotatedString {
        val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)
        var exampleString = buildAnnotatedString {
            withStyle(boldStyle) {
                append(letter)
            }
            append(" ")
            append(exampleStr)
        }

        return exampleString
    }
}