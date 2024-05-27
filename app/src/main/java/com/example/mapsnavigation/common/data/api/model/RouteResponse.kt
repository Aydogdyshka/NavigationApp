package com.example.mapsnavigation.common.data.api.model

data class RouteResponse(
    val traffic: String,
    val route: ApiRoute
)

data class ApiRoute(
    val legs: List<Leg>,
    val flags: Flags
)

data class Leg(
    val status: String,
    val steps: List<ApiStep>
)

data class ApiStep(
    val duration: Double,
    val length: Double,
    val polyline: Polyline,
    val mode: String,
    val waitingDuration: Double
)

data class Polyline(
    val points: List<List<Double>>
)

data class Flags(
    val hasTolls: Boolean,
    val hasNonTransactionalTolls: Boolean
)
