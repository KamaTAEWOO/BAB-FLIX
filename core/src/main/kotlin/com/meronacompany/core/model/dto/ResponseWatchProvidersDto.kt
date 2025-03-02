package com.meronacompany.core.model.dto

import com.google.gson.annotations.SerializedName
import com.meronacompany.domain.model.ResponseWatchProvidersData

data class ResponseWatchProvidersDto(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: Map<String, ProviderRegion>?
) {
    fun toModel() : ResponseWatchProvidersData {
        return ResponseWatchProvidersData(
            id = id,
            results = results
        )
    }
}

data class ProviderRegion(
    @SerializedName("link") val link: String?,
    @SerializedName("flatrate") val flatrate: List<ProviderDetails>?
)

data class ProviderDetails(
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("display_priority") val displayPriority: Int
)