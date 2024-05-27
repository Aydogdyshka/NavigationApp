package com.example.mapsnavigation.common.data.api.model.mappers

import com.example.mapsnavigation.common.data.api.model.RouteResponse
import com.example.mapsnavigation.common.domain.model.Route
import javax.inject.Inject

class RouteMapper @Inject constructor(): ApiMapper<RouteResponse, Route> {
    override fun mapToDomain(apiEntity: RouteResponse): Route {
        val leg = apiEntity.route.legs.firstOrNull()
        val points = leg?.steps?.flatMap { it.polyline.points }
        val totalDuration = leg?.steps?.sumOf { it.duration }
        val totalLength = leg?.steps?.sumOf { it.length }
        return Route(
            duration = totalDuration ?: 0.0,
            length = totalLength ?: 0.0,
            points = points ?: emptyList()
        )
    }
}