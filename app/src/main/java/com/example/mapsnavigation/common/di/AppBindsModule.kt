package com.example.mapsnavigation.common.di

import com.example.mapsnavigation.common.data.repository.YandexGeocodingRepo
import com.example.mapsnavigation.common.data.repository.YandexRouteRepository
import com.example.mapsnavigation.common.domain.RouteRepository
import com.example.mapsnavigation.common.domain.model.GeocodingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AppBindsModule {

    @Binds
    abstract fun bindRepo(yandexRouteRepository: YandexRouteRepository): RouteRepository

    @Binds
    abstract fun bindGeoRepo(geocodingRepo: YandexGeocodingRepo): GeocodingRepository
}