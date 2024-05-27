package com.example.mapsnavigation.common.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapsnavigation.ui.theme.MapsNavigationTheme

@Composable
fun NavigationInfoScreen(
    modifier: Modifier = Modifier,
    duration: String,
    length: String,
    destination: String
){
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "Start: Current location",
                color = Color.Black.copy(0.5f)
            )
            Divider()
            Text(
                text = "Destination: $destination",
                color = Color.Black.copy(0.5f)
            )
            Row {
                Text(text = duration)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = length)
            }
        }
    }
}


@Preview
@Composable
fun NavigationInfo_Preview(){
    MapsNavigationTheme {
        NavigationInfoScreen(duration = "10m", length = "100m", destination = "Destination")
    }
}