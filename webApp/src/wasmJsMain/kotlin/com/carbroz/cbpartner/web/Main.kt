package com.carbroz.cbpartner.web

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.carbroz.cbpartner.data.di.bootstrapModule
import com.carbroz.cbpartner.data.di.dynamicDataModule
import com.carbroz.cbpartner.data.di.networkModule
import com.carbroz.cbpartner.domain.model.dynamic.DynamicDestination
import com.carbroz.cbpartner.feature.dynamic.DynamicScreen
import com.carbroz.cbpartner.feature.dynamic.registry.DynamicRegistry
import com.carbroz.cbpartner.feature.dynamic.store.DynamicStore
import com.carbroz.cbpartner.feature.splash.SplashScreen
import com.carbroz.cbpartner.feature.splash.SplashState
import com.carbroz.cbpartner.feature.splash.SplashStore
import com.carbroz.cbpartner.feature.splash.splashModule
import org.koin.core.context.startKoin

private val koin by lazy {
    startKoin {
        modules(
            networkModule,
            bootstrapModule,
            dynamicDataModule,
            splashModule,
        )
    }.koin
}

@Composable
private fun App() {
    val splashStore = remember { koin.get<SplashStore>() }
    val splashState by splashStore.state.collectAsState()

    MaterialTheme {
        when (val state = splashState) {
            is SplashState.Success -> {
                // Bootstrap owns the first navigation decision. From here,
                // navigation is represented by a DynamicDestination: screen,
                // template contract and endpoint. The DynamicScreen stays the
                // single host; subsequent destinations replace this contract.
                val startup = state.output.startup
                val destination = remember(startup.nextScreen) {
                    DynamicDestination(
                        screenId = startup.nextScreen.screenId,
                        templateId = startup.nextScreen.templateId,
                        templateType = startup.nextScreen.templateType,
                        endpoint = startup.nextScreen.endpoint,
                        method = startup.nextScreen.method,
                        authentication = startup.nextScreen.authentication,
                    )
                }
                val dynamicStore = remember(destination) {
                    DynamicStore(
                        repository = koin.get(),
                        destination = destination,
                    )
                }
                val registry = remember { DynamicRegistry().registerDefaults() }

                DisposableEffect(dynamicStore) {
                    onDispose { dynamicStore.close() }
                }

                DynamicScreen(
                    destination = destination,
                    store = dynamicStore,
                    registry = registry,
                )
            }

            else -> SplashScreen(store = splashStore)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(viewportContainerId = "webApp") {
        App()
    }
}
