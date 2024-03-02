package com.example.easyweather.di

import com.example.easyweather.data.location.LocationTracker
import com.example.easyweather.data.location.LocationTrackerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun provideLocationTracker(locationTrackerImpl: LocationTrackerImpl): LocationTracker
}