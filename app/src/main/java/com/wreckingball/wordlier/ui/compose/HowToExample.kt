package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun HowToExample(
    imageId: Int,
    letter: String,
    text: String,
    buildExample: (String, String) -> AnnotatedString,
) {
    Image(
        modifier = Modifier
            .padding(top = MaterialTheme.dimensions.NormalSpace)
            .size(width = 200.dp, height = MaterialTheme.dimensions.ExampleHeight),
        painter = painterResource(id = imageId),
        contentDescription = stringResource(id = R.string.example),
    )
    Text(
        text = buildExample(letter, text),
    )
}

@Preview(name = "How to play example")
@Composable
fun HowToExamplePreview() {
    Column {
        HowToExample(
            imageId = R.drawable.weary,
            letter = stringResource(id = R.string.w),
            text = stringResource(id = R.string.correct_spot),
            buildExample = { _, _ -> buildAnnotatedString { append("W is in the word.") } },
        )
    }
}