package com.example.mapsnavigation.common.presentation.views


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapsnavigation.ui.theme.MapsNavigationTheme

@Composable
fun StartButton(
    onStart: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            { onStart() },
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Start")
        }
    }
}

@Composable
fun NoPermissionScreen(onAskPermission: () -> Unit){
    Column(Modifier.fillMaxWidth()) {
        Button(
            {onAskPermission()},
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Ask permission")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartDialogPreview() {
    MapsNavigationTheme {
        Column {
            StartButton {

            }
            NoPermissionScreen {

            }
        }
    }
}