package com.example.mapsnavigation.common.presentation.views

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsnavigation.common.presentation.MapEvent
import com.example.mapsnavigation.common.presentation.MapsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapView(mapsViewModel: MapsViewModel = viewModel()){
    val context = LocalContext.current
    val searchLocation = "Екатеренбург, ул. Восточная, 7Г"
    val map = rememberMapViewLifecycle()
    val state by mapsViewModel.state.collectAsState()

    val locationPermissionsState = rememberPermissionState(
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ granted ->
        if (granted) {

        } else {
            Toast.makeText(context, "Location permission is not given", Toast.LENGTH_LONG).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column {
            AndroidView(
                modifier = Modifier.weight(1f),
                factory = {
                    map.apply {}
                },
                update = {
                    state.route?.let { route ->
                        if (route.points.isNotEmpty()) {
                            val polyline = Polyline(
                                route.points.map { list ->
                                    Point(list.first(), list.last())
                                }
                            )
                            map.mapWindow.map.mapObjects.addPolyline(polyline)
                            val position = map.mapWindow.map.cameraPosition(
                                Geometry.fromPolyline(polyline),
                                0f,
                                0f,
                                null
                            )
                            map.mapWindow.map.move(position)
                        }
                    }
                }
            )
            if (locationPermissionsState.status.isGranted) {
                StartButton {
                    mapsViewModel.onEvent(MapEvent.StartEvent(
                        location = searchLocation
                    ))
                }
            } else {
                NoPermissionScreen {
                    locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
        state.route?.let {
            NavigationInfoScreen(
                modifier = Modifier.align(Alignment.TopCenter),
                duration = TimeUnit.SECONDS.toMinutes(
                    it.duration.toLong()
                ).toString() + "seconds",
                length = "${it.length.toLong()} meters",
                destination = searchLocation
            )
        }

        if (state.loading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.2f)),
                contentAlignment = Alignment.Center,
            ){
                CircularProgressIndicator()
            }
        }
    }
}