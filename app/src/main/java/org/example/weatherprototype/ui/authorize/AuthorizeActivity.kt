package org.example.weatherprototype.ui.authorize

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.example.weatherprototype.R
import org.example.weatherprototype.databinding.ActivityAuthorizeBinding
import org.example.weatherprototype.utils.EventObserver


@AndroidEntryPoint
class AuthorizeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorizeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: AuthorizeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizeBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        initFusedLocationClient()
        setupActionBar()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.showSnackbarMessageId.observe(this,
            EventObserver { showSnackbar(it) })
        viewModel.showSnackbarMessage.observe(this,
            EventObserver { showSnackbar(it) })
        viewModel.showInvalidPasswordError.observe(this,
            EventObserver { showPasswordInvalidSnackbar() })
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showSnackbar(@StringRes stringId: Int) {
        Snackbar.make(binding.root, stringId, Snackbar.LENGTH_LONG).show()
    }

    private fun setupListeners() {
        binding.textInputLayoutAuthorizePassword.setEndIconOnClickListener {
            showPasswordInvalidSnackbar()
        }
    }
    private fun showPasswordInvalidSnackbar() {
        Snackbar.make(binding.root, R.string.msg_password_invalid, Snackbar.LENGTH_LONG)
            .apply {
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines =
                    4
            }
            .show()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarAuthorize)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initFusedLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val location = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        viewModel.setLocation(location.latitude, location.longitude)
                    }
                }
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            viewModel.setLocation(lastLocation.latitude, lastLocation.longitude)
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), ACCESS_LOCATION_GRANTED_CODE
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_LOCATION_GRANTED_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getLastLocation()
    }

    companion object {
        const val ACCESS_LOCATION_GRANTED_CODE = 101
    }
}