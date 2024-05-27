package com.example.mapsnavigation.common.data.repository

import com.example.mapsnavigation.BuildConfig
import com.example.mapsnavigation.common.data.api.GeocodingApiService
import com.example.mapsnavigation.common.domain.model.GeocodingRepository
import java.net.URLEncoder
import javax.inject.Inject

class YandexGeocodingRepo @Inject constructor(
    private val geocodingApiService: GeocodingApiService
): GeocodingRepository {
    override suspend fun geocode(address: String): Pair<Double, Double> {
        val response = geocodingApiService.geocode(
            apiKey = BuildConfig.GEO_API_KEY,
            geocode = address,
            format = "json"
        )
        val locationString = response.response.GeoObjectCollection.featureMember.first().GeoObject.Point.pos
        val parts = locationString.split(" ")
        val first = parts[0].toDouble()
        val second = parts[1].toDouble()
        return Pair(first, second)
    }
}