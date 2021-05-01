package com.mahmoudelshahat.forecastapp.ui.current_weather


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mahmoudelshahat.forecastapp.R
import com.mahmoudelshahat.forecastapp.databinding.FragmentCurrentWeatherBinding
import com.mahmoudelshahat.forecastapp.ui.base.MainScopedFragment
import com.mahmoudelshahat.nytimesmp.util.EspressoIdlingResource
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class CurrentWeatherFragment : MainScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherModelFactory by instance()

    lateinit var viewModel: CurrentWeatherViewModel

    lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        val binding: FragmentCurrentWeatherBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_current_weather, container, false
            )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        bindUI()
        return binding.root

    }


    private fun bindUI() = launch {

        //location
        val currentWeatherLocation = viewModel.location.await()
        currentWeatherLocation.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            viewModel.currentLocation.value = it
        })


        //weather
        val currentWeatherOperation = viewModel.weather.await()
        currentWeatherOperation.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            viewModel.currentWeather.value = it
            viewModel.stillLoading.value = false

            EspressoIdlingResource.decrement()


        })


    }


}