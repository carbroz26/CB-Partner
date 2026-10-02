package com.carbroz.cbpartner.domain.model.bootstrap

data class StartupConfig(
    val authenticated: Boolean,
    val nextScreen: NextScreenConfig,
)

data class NextScreenConfig(
    val screenId: String,
    val templateId: String,
    val templateType: String,
    val endpoint: String,
    val method: String,
    val authentication: String,
)
