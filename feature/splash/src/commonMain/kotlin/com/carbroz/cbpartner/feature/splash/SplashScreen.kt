package com.carbroz.cbpartner.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(
    store: SplashStore,
    modifier: Modifier = Modifier,
) {
    val state by store.state.collectAsState()

    LaunchedEffect(Unit) {
        store.accept(SplashIntent.LoadBootstrap)
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "CarBroz Partner",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Partner App",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(24.dp))

        when (state) {
            SplashState.Initial,
            SplashState.Loading,
            -> CircularProgressIndicator()

            is SplashState.Success -> Unit

            is SplashState.Failure -> {
                val failure = state as SplashState.Failure
                Text(
                    text = failure.message,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = { store.accept(SplashIntent.RetryBootstrap) },
                ) {
                    Text("Retry")
                }
            }
        }
    }
}
