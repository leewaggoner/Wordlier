package com.wreckingball.wordlier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wreckingball.wordlier.ui.theme.WordlierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordlierTheme {
                WordlierApp()
            }
        }
    }
}

