package com.carbroz.cbpartner.data.bootstrap

import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapConfig
import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapFailure
import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapOutput
import com.carbroz.cbpartner.domain.model.bootstrap.FeatureConfig
import com.carbroz.cbpartner.domain.model.bootstrap.MaintenanceConfig
import com.carbroz.cbpartner.domain.model.bootstrap.NextScreenConfig
import com.carbroz.cbpartner.domain.model.bootstrap.StartupConfig
import com.carbroz.cbpartner.domain.model.bootstrap.UpdateConfig

internal fun BootstrapResponseDto.toDomain(): Result<BootstrapOutput> {
    if (status !in 200..299) {
        return Result.failure(BootstrapFailure.Api(code, message))
    }
    if (code != "SUCCESS") {
        return Result.failure(BootstrapFailure.Api(code, message))
    }

    val value = data ?: return invalid("Missing bootstrap data")
    val config = value.config ?: return invalid("Missing bootstrap config")
    val maintenance = config.maintenance ?: return invalid("Missing maintenance config")
    val update = config.update ?: return invalid("Missing update config")
    val features = config.features ?: return invalid("Missing feature config")
    val startup = value.startup ?: return invalid("Missing startup config")
    val nextScreen = startup.nextScreen ?: return invalid("Missing next screen config")

    return Result.success(
        BootstrapOutput(
            config = BootstrapConfig(
                version = config.version ?: return invalid("Missing bootstrap version"),
                maintenance = MaintenanceConfig(
                    enabled = maintenance.enabled ?: return invalid("Missing maintenance enabled flag"),
                    title = maintenance.title,
                    message = maintenance.message,
                ),
                update = UpdateConfig(
                    required = update.required ?: return invalid("Missing update required flag"),
                    optional = update.optional ?: return invalid("Missing update optional flag"),
                    minimumVersion = update.minimumVersion ?: return invalid("Missing minimum app version"),
                    latestVersion = update.latestVersion ?: return invalid("Missing latest app version"),
                    storeUrl = update.storeUrl,
                ),
                features = FeatureConfig(
                    registrationEnabled = features.registrationEnabled ?: return invalid("Missing registration flag"),
                    individualPartnerEnabled = features.individualPartnerEnabled ?: return invalid("Missing individual partner flag"),
                    organizationPartnerEnabled = features.organizationPartnerEnabled ?: return invalid("Missing organization partner flag"),
                ),
            ),
            startup = StartupConfig(
                authenticated = startup.authenticated ?: return invalid("Missing authenticated flag"),
                nextScreen = NextScreenConfig(
                    screenId = nextScreen.screenId ?: return invalid("Missing screen id"),
                    templateId = nextScreen.templateId ?: return invalid("Missing template id"),
                    templateType = nextScreen.templateType ?: return invalid("Missing template type"),
                    endpoint = nextScreen.endpoint ?: return invalid("Missing screen endpoint"),
                    method = nextScreen.method ?: return invalid("Missing screen method"),
                    authentication = nextScreen.authentication ?: return invalid("Missing screen authentication"),
                ),
            ),
        ),
    )
}

private fun invalid(reason: String): Result<BootstrapOutput> =
    Result.failure(BootstrapFailure.InvalidResponse(reason))
