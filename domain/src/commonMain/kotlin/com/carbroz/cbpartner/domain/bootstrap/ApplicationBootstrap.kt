package com.carbroz.cbpartner.domain.bootstrap

import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapOutput

interface ApplicationBootstrap {
    suspend operator fun invoke(): Result<BootstrapOutput>
}
