package com.wreckingball.wordlier.ui.howto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.wreckingball.wordlier.ui.compose.HowToDesc
import com.wreckingball.wordlier.ui.compose.HowToExamples
import com.wreckingball.wordlier.ui.theme.dimensions
import org.koin.androidx.compose.getViewModel

@Composable
fun HowToPlay(
    viewModel: HowToViewModel = getViewModel(),
) {
    HowToContent(
        buildRules = viewModel::buildRulesString,
        buildExample = viewModel::buildExampleString,
    )
}

@Composable
private fun HowToContent(
    buildRules: (List<String>) -> AnnotatedString,
    buildExample: (String, String) -> AnnotatedString,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimensions.IntroPadding),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.NormalSpace),
            text = stringResource(id = R.string.how_to_play),
            fontSize = MaterialTheme.dimensions.IntroTitleSize,
            fontWeight = FontWeight.Bold,
        )

        HowToDesc(buildRules = buildRules)

        HowToExamples(buildExample = buildExample)

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.BiggerSpace),
            text = stringResource(id = R.string.new_puzzle),
        )
    }
}

@Preview(name = "HowToPlay Content")
@Composable
fun HowToPlayContentPreview() {
    HowToContent(
        buildRules = { buildAnnotatedString { append("These are the rules.") } },
        buildExample = { _, _ -> buildAnnotatedString { append("W is in the word.") } },
    )
}