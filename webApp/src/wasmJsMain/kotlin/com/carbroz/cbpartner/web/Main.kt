package com.carbroz.cbpartner.web

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.carbroz.cbpartner.data.di.bootstrapModule
import com.carbroz.cbpartner.data.di.networkModule
import com.carbroz.cbpartner.feature.splash.SplashScreen
import com.carbroz.cbpartner.feature.splash.SplashStore
import com.carbroz.cbpartner.feature.splash.splashModule
import org.koin.core.context.startKoin

private val koin by lazy {
    startKoin {
        modules(
            networkModule,
            bootstrapModule,
            splashModule,
        )
    }.koin
}

@Composable
private fun App() {
    MaterialTheme {
        SplashScreen(
            store = koin.get<SplashStore>(),
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(viewportContainerId = "webApp") {
        App()
    }
}
