package com.carbroz.cbpartner.data.bootstrap

import com.carbroz.cbpartner.domain.bootstrap.ApplicationBootstrap
import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapOutput

class DefaultApplicationBootstrap(
    private val remoteDataSource: BootstrapRemoteDataSource,
) : ApplicationBootstrap {
    override suspend fun invoke(): Result<BootstrapOutput> = remoteDataSource.fetch()
}
