package algorithm.kelvin.aptoid.app_google_map_mapbox

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import com.utsman.smartmarker.mapbox.MarkerOptions
import com.utsman.smartmarker.mapbox.addMarker
import kotlinx.android.synthetic.main.activity_map_box.*

@SuppressLint("Registered")
class MapBoxActivity : AppCompatActivity() {
    private val token = "pk.eyJ1Ijoia2VsdmluaHQzNzMiLCJhIjoiY2s2M2VpcDVwMDN6bzNtbXBlMXQ3NzR0aSJ9.wcPbpTBuWV6ribe6USX2lw"
    private val monasLatLng = LatLng(6.175392, 106.827153)
    private val binusSyahdanLatLng = LatLng(6.2019, 106.7809)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, token)
        setContentView(R.layout.activity_map_box)

        layout_mapBox.getMapAsync { mapbox ->
            mapbox.setStyle(Style.MAPBOX_STREETS) { style ->
                val markerOptionMonas = MarkerOptions.Builder()
                    .setId("monas")
                    .setIcon(R.drawable.mapbox_marker_icon_default)
                    .setPosition(monasLatLng)
                    .build(this)

                val markerOptionBinusSyahdan = MarkerOptions.Builder()
                    .setId("binus_syahdan")
                    .setIcon(R.drawable.mapbox_marker_icon_default)
                    .setPosition(binusSyahdanLatLng)
                    .build(this)

                mapbox.addMarker(markerOptionMonas)
                mapbox.addMarker(markerOptionBinusSyahdan)
                mapbox.animateCamera(CameraUpdateFactory.newLatLngZoom(monasLatLng, 13.0))
            }
        }
    }
}
