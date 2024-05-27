package com.example.mapsnavigation.common.domain.model

data class Route(
    val duration: Double,
    val length: Double,
    val points: List<List<Double>>,
)
