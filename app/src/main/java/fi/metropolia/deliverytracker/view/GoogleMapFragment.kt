package fi.metropolia.deliverytracker.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import fi.metropolia.deliverytracker.R
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import fi.metropolia.deliverytracker.model.Route
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.android.synthetic.main.fragment_google_map.*
import fi.metropolia.deliverytracker.util.DirectionFinder
import java.io.UnsupportedEncodingException


class GoogleMapFragment : Fragment(), OnMapReadyCallback, DirectionFinderListener {

    private lateinit var mMap: GoogleMap
    private var originMarkers = arrayListOf<Marker>()
    private var destinationMarkers = arrayListOf<Marker>()
    private var polylinePaths = arrayListOf<Polyline>()
    private var destination = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_google_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            destination = GoogleMapFragmentArgs.fromBundle(it).detination
        }
        sendRequest()
    }

    private fun sendRequest() {
        val origin = "Vieraskuja 5, Espoo"
        try {
            DirectionFinder(this, origin, destination).execute()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.isMyLocationEnabled = true
    }

    override fun onDirectionFinderStart() {
        if (originMarkers.isNotEmpty()) {
            for (marker in originMarkers) {
                marker.remove()
            }
        }

        if (destinationMarkers.isNotEmpty()) {
            for (marker in destinationMarkers) {
                marker.remove()
            }
        }

        if (polylinePaths.isNotEmpty()) {
            for (polyline in polylinePaths) {
                polyline.remove()
            }
        }
    }

    override fun onDirectionFinderSuccess(routeList: List<Route>) {
        polylinePaths = ArrayList()
        originMarkers = ArrayList()
        destinationMarkers = ArrayList()

        for (route in routeList) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16f))
            durationText.text = route.duration?.text
            distanceText.text = route.distance?.text

            originMarkers.add(
                mMap.addMarker(
                    MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(route.startAddress)
                        .position(route.startLocation!!)
                )
            )
            destinationMarkers.add(
                mMap.addMarker(
                    MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .title(route.endAddress)
                        .position(route.endLocation!!)
                )
            )

            val polylineOptions = PolylineOptions().geodesic(true).color(Color.BLUE).width(10f)

            for (i in route.points!!.indices)
                polylineOptions.add(route.points!![i])

            polylinePaths.add(mMap.addPolyline(polylineOptions))
        }
    }
}
