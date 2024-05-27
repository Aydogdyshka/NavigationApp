package com.example.mapsnavigation.common.presentation

sealed class MapEvent {
    data class StartEvent(val location: String): MapEvent()

}