package com.example.mapsnavigation.common.data.api

import com.example.mapsnavigation.common.data.api.model.RouteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //longitude, latitude
    @GET("route")
    suspend fun getRoute(
        @Query("waypoints") waypoints: String,
        @Query("apikey")apiKey: String
    ): RouteResponse
}