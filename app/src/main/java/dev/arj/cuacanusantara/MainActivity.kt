package dev.arj.cuacanusantara

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.arj.cuacanusantara.databinding.ActivityMainBinding
import dev.arj.cuacanusantara.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<WeatherViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFullScreen()

        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main)
            .apply {
                lifecycleOwner = this@MainActivity
                viewModel = this@MainActivity.viewModel
            }


        viewModel.fetchCurrentWeather("-6.216774", "106.523054")
    }

    private fun setupFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}