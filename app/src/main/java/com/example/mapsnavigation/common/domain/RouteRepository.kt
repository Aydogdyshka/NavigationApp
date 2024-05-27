package com.example.mapsnavigation.common.domain

import com.example.mapsnavigation.common.domain.model.Route

interface RouteRepository {
    suspend fun getRoute(
        fLongitude: Double,
        fLatitude: Double,
        tLongitude: Double,
        tLatitude: Double
    ): Route

}