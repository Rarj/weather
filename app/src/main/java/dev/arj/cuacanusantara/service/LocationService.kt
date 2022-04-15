package dev.arj.cuacanusantara.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*
import dev.arj.cuacanusantara.MainActivity
import dev.arj.cuacanusantara.R

class LocationService : Service() {

    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 60000

    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreate() {
        super.onCreate()

        initData()
    }

    private var lastLatitude = ""
    private var lastLongitude = ""

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val currentLocation: Location = locationResult.lastLocation
            val latitude = currentLocation.latitude.toString()
            val longitude = currentLocation.longitude.toString()

            val isLocationNotChanged = lastLatitude == latitude && lastLongitude == longitude
            if (isLocationNotChanged) {
                return
            }

            lastLatitude = latitude
            lastLongitude = longitude

            val intent = Intent("GPSLocationUpdates")
            intent.putExtra("lat", lastLatitude)
            intent.putExtra("long", lastLongitude)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        }
    }

    private fun initData() {
        locationRequest = LocationRequest.create()
        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        prepareForegroundNotification()
        startLocationUpdates()

        return START_STICKY
    }

    private fun startLocationUpdates() {
        Looper.myLooper()?.let {
            fusedLocationProvider.requestLocationUpdates(
                this.locationRequest,
                this.locationCallback, it
            )
        }
    }

    private fun prepareForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Location Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            SERVICE_LOCATION_REQUEST_CODE,
            notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentTitle("Cuaca Nusantara use your location updates to provide accurate forecasting.")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(LOCATION_SERVICE_NOTIF_ID, notification)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProvider.removeLocationUpdates(locationCallback)
    }

    companion object {
        const val CHANNEL_ID = "channel_weather_id"
        const val SERVICE_LOCATION_REQUEST_CODE = 100
        const val LOCATION_SERVICE_NOTIF_ID = 200
    }
}