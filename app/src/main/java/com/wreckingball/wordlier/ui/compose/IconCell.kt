package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.wreckingball.wordlier.R
import com.wreckingball.wordlier.ui.theme.dimensions

@Composable
fun IconCell(
    icon: Painter,
    command: String,
    modifier: Modifier = Modifier,
    cellHeight: Dp = MaterialTheme.dimensions.KeyboardLetterBoxDims,
    cellWidth: Dp = MaterialTheme.dimensions.ActionBoxWidth,
    iconHeight: Dp = MaterialTheme.dimensions.BackIconHeight,
    iconWidth: Dp = MaterialTheme.dimensions.BackIconWidth,
    color: Color = Color.LightGray,
    onClick: ((String) -> Unit)? = null
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(cellWidth, cellHeight)
                .border(
                    width = MaterialTheme.dimensions.LetterBoxBorderDims,
                    color = Color.Black,
                    shape = RoundedCornerShape(MaterialTheme.dimensions.LetterBoxCornerDims)
                )
                .clip(RoundedCornerShape(MaterialTheme.dimensions.LetterBoxCornerDims))
                .background(
                    color = color,
                )
                .clickable {
                    onClick?.let {
                        onClick(command)
                    }
                },
        ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = "delete",
            modifier = Modifier.size(width = iconWidth, height = iconHeight),
        )
    }
}

@Preview
@Composable
fun IconCellPreview() {
    IconCell(
        icon = painterResource(id = R.drawable.ic_delete),
        command = "",
    )
}