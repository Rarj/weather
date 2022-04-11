package dev.arj.cuacanusantara

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.LocationServices
import dev.arj.cuacanusantara.databinding.ActivityMainBinding
import dev.arj.cuacanusantara.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val requestCodeLocation = 100
    }

    private val viewModel by viewModel<WeatherViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFullScreen()
        setupDataBinding()

        if (isLocationEnabled()) {
            checkLocationPermission()
        } else {
            Toast.makeText(this, "Aktifkan lokasi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main)
            .apply {
                lifecycleOwner = this@MainActivity
                viewModel = this@MainActivity.viewModel
            }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkLocationPermission() {
        val isCoarseLocationGranted = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isFineLocationGranted = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!isCoarseLocationGranted && !isFineLocationGranted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestCodeLocation
            )
        } else {
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else {
            Toast.makeText(this, "Izinkan untuk mengakses lokasi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLocation() {
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
            val location: Location? = task.result
            if (location != null) {
                fetchLocation(location.latitude.toString(), location.longitude.toString())
            }
        }
    }

    private fun fetchLocation(latitude: String, longitude: String) {
        viewModel.fetchCurrentWeather(
            latitude = latitude,
            longitude = longitude
        )
    }

    private fun setupFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}