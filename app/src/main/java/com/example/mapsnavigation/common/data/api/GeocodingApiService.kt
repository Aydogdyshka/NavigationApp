package com.example.mapsnavigation.common.data.api

import com.example.mapsnavigation.common.data.api.model.YandexGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("1.x")
    suspend fun geocode(
        @Query("apikey") apiKey: String?,
        @Query("geocode") geocode: String?,
        @Query("format") format: String?
    ): YandexGeocodingResponse

}