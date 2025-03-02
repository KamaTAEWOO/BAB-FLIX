package com.meronacompany.domain.model

data class ResponseWatchProvidersData(
    val id: Int,
    val results: Map<String, Any>?
)

data class ProviderRegion(
    val link: String?,
    val flatrate: List<ProviderDetails>?
)

data class ProviderDetails(
    val logoPath: String?,
    val providerId: Int,
    val providerName: String,
    val displayPriority: Int
)
