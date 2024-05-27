package com.example.mapsnavigation.common.domain.model

interface GeocodingRepository {
    suspend fun geocode(address: String): Pair<Double, Double>
}