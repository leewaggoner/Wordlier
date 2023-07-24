package com.wreckingball.wordlier.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingball.wordlier.R

@Composable
fun IconCell(
    icon: Painter,
    command: String,
    modifier: Modifier = Modifier,
    cellHeight: Int = 32,
    cellWidth: Int = 42,
    iconHeight: Int = 22,
    iconWidth: Int = 32,
    color: Color = Color.White,
    onClick: ((String) -> Unit)? = null
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(cellWidth.dp, cellHeight.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
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
            modifier = Modifier.size(width = iconWidth.dp, height = iconHeight.dp),
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