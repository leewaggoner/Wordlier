package com.wreckingball.wordlier.ui.howto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun HowToExamples(buildExample: (String, String) -> AnnotatedString) {
    Text(
        modifier = Modifier
            .padding(top = MaterialTheme.dimensions.NormalSpace),
        text = stringResource(id = R.string.examples),
        fontSize = MaterialTheme.dimensions.IntroNormalTextSize,
        fontWeight = FontWeight.SemiBold,
    )

    HowToExample(
        imageId = R.drawable.weary,
        letter = stringResource(id = R.string.w),
        text = stringResource(id = R.string.correct_spot),
        buildExample = buildExample)

    HowToExample(
        imageId = R.drawable.pills,
        letter = stringResource(id = R.string.i),
        text = stringResource(id = R.string.wrong_spot),
        buildExample = buildExample)

    HowToExample(
        imageId = R.drawable.vague,
        letter = stringResource(id = R.string.u),
        text = stringResource(id = R.string.not_in_word),
        buildExample = buildExample)
}

@Preview(name = "How to play examples")
@Composable
fun HowToExamplesPreview() {
    Column {
        HowToExamples(
            buildExample = { _, _ -> buildAnnotatedString { append("W is in the word.") } },
        )
    }
}