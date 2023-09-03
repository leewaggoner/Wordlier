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
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun HowToDesc(buildRules: (List<String>) -> AnnotatedString) {
    Text(
        modifier = Modifier
            .padding(top = MaterialTheme.dimensions.NormalSpace),
        text = stringResource(id = R.string.instructions),
        fontSize = MaterialTheme.dimensions.IntroDescriptionSize,
    )
    val rulesLines = listOf(
        stringResource(id = R.string.bullet1),
        stringResource(id = R.string.bullet2),
    )
    Text(
        modifier = Modifier
            .padding(top = MaterialTheme.dimensions.NormalSpace),
        text = buildRules(rulesLines),
    )
}

@Preview(name = "How to play description")
@Composable
fun HowToDescPreview() {
    Column {
        HowToDesc(
            buildRules = { buildAnnotatedString { append("These are the rules.") } },
        )
    }
}