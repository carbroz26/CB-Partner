package com.carbroz.cbpartner.domain.model.bootstrap

data class BootstrapConfig(
    val version: String,
    val maintenance: MaintenanceConfig,
    val update: UpdateConfig,
    val features: FeatureConfig,
)

data class MaintenanceConfig(
    val enabled: Boolean,
    val title: String?,
    val message: String?,
)

data class UpdateConfig(
    val required: Boolean,
    val optional: Boolean,
    val minimumVersion: String,
    val latestVersion: String,
    val storeUrl: String?,
)

data class FeatureConfig(
    val registrationEnabled: Boolean,
    val individualPartnerEnabled: Boolean,
    val organizationPartnerEnabled: Boolean,
)
