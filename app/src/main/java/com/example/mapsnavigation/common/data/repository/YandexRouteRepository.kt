package com.example.mapsnavigation.common.data.repository

import com.example.mapsnavigation.BuildConfig
import com.example.mapsnavigation.common.data.api.ApiService
import com.example.mapsnavigation.common.data.api.model.mappers.RouteMapper
import com.example.mapsnavigation.common.domain.RouteRepository
import com.example.mapsnavigation.common.domain.model.Route
import javax.inject.Inject

class YandexRouteRepository @Inject constructor(
    private val apiService: ApiService,
    private val routeMapper: RouteMapper
): RouteRepository {
    override suspend fun getRoute(
        fLongitude: Double,
        fLatitude: Double,
        tLongitude: Double,
        tLatitude: Double
    ): Route {
        val waypoints = "$fLatitude,$fLongitude|$tLatitude,$tLongitude"
        val response = apiService.getRoute(waypoints, BuildConfig.NAV_API_KEY)
        return routeMapper.mapToDomain(response)
    }
}