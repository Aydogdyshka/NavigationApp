package com.example.mapsnavigation.common.presentation

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapsnavigation.common.domain.RouteRepository
import com.example.mapsnavigation.common.domain.model.GeocodingRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.log

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: RouteRepository,
    private val geocodingRepository: GeocodingRepository,
    private val fusedLocationProviderClient: FusedLocationProviderClient
): ViewModel(){

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    fun onEvent(event: MapEvent){
        when(event) {
            is MapEvent.StartEvent -> {
                prepare(event.location)
            }
        }
    }

    private fun prepare(locationString: String){
        Log.d(TAG, "prepare: called")
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = false) }
            try {
                val currentLocation = getLatestLocation(fusedLocationProviderClient) ?: return@launch
                val location = geocodingRepository.geocode(locationString)
                val route = repository.getRoute(
                    currentLocation.longitude,
                    currentLocation.latitude,
                    location.first,
                    location.second
                )
                _state.update {
                    it.copy(route = route, loading = false, error = false)
                }
            } catch (e: Exception) {
                _state.update { it.copy(loading = false, error = true) }
                Log.d(TAG, "prepare: Failed to load nav data $e")
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getLatestLocation(fusedLocationClient: FusedLocationProviderClient): Location? {
        return withContext(Dispatchers.Default) {
            suspendCancellableCoroutine { continuation ->
                try {
                    // Get the last known location asynchronously
                    val task = fusedLocationClient.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        null
                    )
                    task.addOnSuccessListener { location ->
                        continuation.resume(location)
                    }.addOnFailureListener { e ->
                        continuation.resumeWithException(e)
                    }
                } catch (e: Exception) {
                    // Handle exceptions such as security exceptions or no last known location available
                    continuation.resumeWithException(e)
                }

                // Cleanup code if coroutine is cancelled
                continuation.invokeOnCancellation {
                    // If needed, perform cleanup operations here
                }
            }
        }
    }

    companion object {
        const val TAG = "MapsViewModel"
    }
}
