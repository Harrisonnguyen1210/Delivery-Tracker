package fi.metropolia.deliverytracker.view

import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import fi.metropolia.deliverytracker.R
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import fi.metropolia.deliverytracker.model.Route
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import kotlinx.android.synthetic.main.fragment_google_map.*
import fi.metropolia.deliverytracker.util.DirectionFinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException
import kotlin.coroutines.CoroutineContext

/**
 * Fragment screen to show Google map, including direction, estimated time, destination address...
 */
class GoogleMapFragment : Fragment(), OnMapReadyCallback, DirectionFinderListener, CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private lateinit var mMap: GoogleMap
    private var originMarkers = arrayListOf<Marker>()
    private var destinationMarkers = arrayListOf<Marker>()
    private var polylinePaths = arrayListOf<Polyline>()
    private var destination = ""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var geoCoder: Geocoder
    private var requestId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_google_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        geoCoder = Geocoder(context!!)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        //Call back when current location is retrieved
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                //Encode current address from coordinates to text address
                val address = geoCoder.getFromLocation(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude, 1)[0]
                sendRequest(address.getAddressLine(0))
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            destination = GoogleMapFragmentArgs.fromBundle(it).detination
            requestId = GoogleMapFragmentArgs.fromBundle(it).requestId
            infoText.text = GoogleMapFragmentArgs.fromBundle(it).info
            addressText.text = destination
        }
        startLocationUpdates()
        finishButton.setOnClickListener {
            launch {
                DeliveryTrackDatabase(context!!).requestDao().finishRequest("Finished", requestId)
                val action = GoogleMapFragmentDirections.actionGoogleMapFragmentToRequestDetail()
                action.requestId = requestId
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    //Update current location
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    //Start direction finder
    private fun sendRequest(origin: String) {
        try {
            DirectionFinder(this, origin, destination).execute()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
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
