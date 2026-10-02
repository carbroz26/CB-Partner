package com.carbroz.cbpartner.feature.splash

import org.koin.dsl.module

val splashModule = module {
    factory { SplashStore(get()) }
}
