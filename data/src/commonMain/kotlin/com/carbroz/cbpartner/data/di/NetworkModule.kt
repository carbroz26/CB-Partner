package com.carbroz.cbpartner.data.di

import com.carbroz.cbpartner.data.network.NetworkConfig
import com.carbroz.cbpartner.data.network.RemoteDataSource
import io.ktor.client.HttpClient
import org.koin.dsl.module

/**
 * Data-layer runtime network infrastructure.
 */
val networkModule = module {
    single {
        NetworkConfig(
            baseUrl = "http://localhost:3000",
        )
    }
    single { HttpClient() }
    single { RemoteDataSource(get(), get()) }
}
