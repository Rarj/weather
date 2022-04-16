package dev.arj.cuacanusantara.ui.weather

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import dev.arj.cuacanusantara.R
import dev.arj.cuacanusantara.databinding.FragmentWeatherBinding
import dev.arj.cuacanusantara.network.ViewState
import dev.arj.cuacanusantara.service.LocationService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {

    companion object {
        const val requestCodeLocation = 100
    }

    private val viewModel by viewModel<WeatherViewModel>()
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_weather,
            container,
            false
        )
        binding.lifecycleOwner = this@WeatherFragment
        binding.viewModel = this.viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isLocationEnabled()) {
            checkLocationPermission()
        } else {
            Toast.makeText(context, "Aktifkan lokasi", Toast.LENGTH_SHORT).show()
        }

        setAnimationWeather()

        binding.buttonSearch.setOnClickListener {
            it.findNavController().navigate(R.id.action_home_to_search)
        }
    }

    private fun setAnimationWeather() {
        lifecycleScope.launch {
            viewModel.weatherState.collect { state ->
                when (state) {
                    is ViewState.Success -> {
                        state.data?.getStatusWeatherAnimation()?.let { rawRes ->
                            binding.imageWeather.apply {
                                setAnimation(rawRes)
                                playAnimation()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkLocationPermission() {
        val isCoarseLocationGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isFineLocationGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!isCoarseLocationGranted && !isFineLocationGranted) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    requestCodeLocation
                )
            }
        } else {
            val serviceIntent = Intent(context, LocationService::class.java)
            context?.let {
                ContextCompat.startForegroundService(it, serviceIntent)

                LocalBroadcastManager.getInstance(it).registerReceiver(
                    broadcastReceiverLocation, IntentFilter("GPSLocationUpdates")
                )
            }
        }
    }

    private val broadcastReceiverLocation = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            val latitude = intent?.getStringExtra("lat")
            val longitude = intent?.getStringExtra("long")

            viewModel.fetchCurrentWeather(latitude.toString(), longitude.toString())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val serviceIntent = Intent(context, LocationService::class.java)
            context?.let {
                ContextCompat.startForegroundService(it, serviceIntent)

                LocalBroadcastManager.getInstance(it).registerReceiver(
                    broadcastReceiverLocation, IntentFilter("GPSLocationUpdates")
                )
            }
        } else {
            Toast.makeText(context, "Izinkan untuk mengakses lokasi", Toast.LENGTH_SHORT).show()
        }
    }

}