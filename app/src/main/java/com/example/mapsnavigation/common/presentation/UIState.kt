package com.example.mapsnavigation.common.presentation

import com.example.mapsnavigation.common.domain.model.Route

data class UIState(
    val route: Route? = null,
    val loading: Boolean = false,
    val error: Boolean = false
)