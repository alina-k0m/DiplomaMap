package by.komarovskaya.diplomamap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import by.komarovskaya.diplomamap.lib.DefaultMapProperties
import by.komarovskaya.diplomamap.lib.Marker
import by.komarovskaya.diplomamap.lib.OpenStreetMap
import by.komarovskaya.diplomamap.lib.ZoomButtonVisibility
import by.komarovskaya.diplomamap.lib.rememberCameraState
import by.komarovskaya.diplomamap.lib.rememberMarkerState
import by.komarovskaya.diplomamap.lib.rememberOverlayManagerState
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.overlay.CopyrightOverlay

@Composable
fun SimplePage() {
    val context = LocalContext.current

    val cameraState = rememberCameraState {
        geoPoint = Coordinates.home
        zoom = 12.0
    }

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    val homeMarkerState = rememberMarkerState(
        geoPoint = Coordinates.home
    )

    val overlayManagerState = rememberOverlayManagerState()

    SideEffect {
        mapProperties = mapProperties
            .copy(isTilesScaledToDpi = true)
            .copy(tileSources = TileSourceFactory.MAPNIK)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties,
        overlayManagerState = overlayManagerState,
        onFirstLoadListener = {
            val copyright = CopyrightOverlay(context)
            overlayManagerState.overlayManager.add(copyright)
        }


    )
    {
        Marker(
            state = homeMarkerState // add marker state
        )
    }
}