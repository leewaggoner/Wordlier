package com.wreckingball.wordlier.ui.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun Login(goHome: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Button(onClick = goHome) {
            Text("Go To Home Page")
        }
        Text(text = "Login Page", color = Color.White)
    }
}
