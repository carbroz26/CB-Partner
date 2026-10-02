package com.carbroz.cbpartner.domain.model.bootstrap

sealed class BootstrapFailure(message: String) : Exception(message) {
    data class Transport(val reason: String) : BootstrapFailure(reason)
    data class Http(val statusCode: Int) : BootstrapFailure("HTTP $statusCode")
    data class Api(val code: String, override val message: String) : BootstrapFailure(message)
    data class Serialization(val reason: String) : BootstrapFailure(reason)
    data class InvalidResponse(val reason: String) : BootstrapFailure(reason)
    data class Unknown(val reason: String) : BootstrapFailure(reason)
}
