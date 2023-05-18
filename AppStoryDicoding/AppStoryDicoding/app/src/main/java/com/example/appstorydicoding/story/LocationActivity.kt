package com.example.appstorydicoding.story

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.appstorydicoding.Const
import com.example.appstorydicoding.R
import com.example.appstorydicoding.Result
import com.example.appstorydicoding.ViewModelFactory
import com.example.appstorydicoding.databinding.ActivityLocationBinding
import com.example.appstorydicoding.databinding.CustomInfoContentsBinding
import com.example.appstorydicoding.response.ListStory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class LocationActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST) {
        }

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val locationViewModel: LocationViewModel by viewModels {
            factory
        }

        val token = intent.getStringExtra("EXTRA_KEY")
        val authToken = "Bearer $token"
        if (token != null) {
            locationViewModel.getStoryWithLocation(1, authToken).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            showMarker(result.data.listStory)
                        }
                        is Result.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.location) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportActionBar?.title = "Location Story"
    }

    private fun showMarker(listStory: List<ListStory>) {
        for (story in listStory) {
            val latLng = LatLng(story.lat, story.lon)

            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
            )?.tag = story
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mMap.setInfoWindowAdapter(this)
        mMap.setOnInfoWindowClickListener { marker ->
            val story: ListStory = marker.tag as ListStory
            infoWindowMarker(story)
        }

        getMyLocation()
        setMapStyle()
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.location_activation),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun infoWindowMarker(story: ListStory) {
        val intent = Intent(this, DetailStoryActivity::class.java)
        intent.putExtra(EXTRA_NAME, story.name)
        intent.putExtra(EXTRA_IMAGE, story.photoUrl)
        intent.putExtra(EXTRA_DESC, story.description)
        intent.putExtra(EXTRA_DATE, Const.getUploadStoryTime(story.createdAt))
        intent.putExtra(EXTRA_LAT, story.lat.toString())
        intent.putExtra(EXTRA_LOT, story.lon.toString())
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View {
        val binding =
            CustomInfoContentsBinding.inflate(LayoutInflater.from(this))
        val data: ListStory = marker.tag as ListStory
        "${data.lat + data.lon}".also { binding.labelLocation.text = it }
        binding.name.text = data.name
        binding.image.setImageBitmap(Const.bitmapFromURL(this, data.photoUrl))
        binding.storyDescription.text = data.description
        binding.storyUploadTime.text = Const.getUploadStoryTime(data.createdAt)
        return binding.root
    }

    companion object {
        private const val TAG = "LocationActivity"
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_DESC = "EXTRA_DESC"
        const val EXTRA_DATE = "EXTRA_DATE"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
        const val EXTRA_LAT = "EXTRA_LAT"
        const val EXTRA_LOT = "EXTRA_LOT"

    }
}