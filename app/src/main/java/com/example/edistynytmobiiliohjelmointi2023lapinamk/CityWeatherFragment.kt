package com.example.edistynytmobiiliohjelmointi2023lapinamk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.AuthFailureError
import com.android.volley.BuildConfig
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.edistynytmobiiliohjelmointi2023lapinamk.databinding.FragmentCityWeatherBinding
import com.example.edistynytmobiiliohjelmointi2023lapinamk.datatypes.cityweather.CityWeather
import com.google.gson.GsonBuilder

/**
 * A simple [Fragment] subclass.
 * Use the [CityWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityWeatherFragment : Fragment() {

    private var _binding: FragmentCityWeatherBinding? = null

    val args: CityWeatherFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("TESTI", "START")
        Log.d("TESTI", args.lat.toString())
        Log.d("TESTI", args.lon.toString())

        getWeatherData()

        // Click listeneri fragmentin sulkemiselle
        binding.buttonCityWeatherReturn.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    fun getWeatherData() {
        // this is the url where we want to get our data from

        val API_KEY = com.example.edistynytmobiiliohjelmointi2023lapinamk.BuildConfig.WEATHERMAP_API_KEY

        val JSON_URL = "https://api.openweathermap.org/data/2.5/weather?lat=${args.lat}&lon=${args.lon}&units=metric&appid=${API_KEY}"

        val gson = GsonBuilder().setPrettyPrinting().create()

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->

                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                Log.d("TESTI", response)

                var item : CityWeather = gson.fromJson(response, CityWeather::class.java)

                val temperature = item.main?.temp
                val humidity = item.main?.humidity
                val windSpeed = item.wind?.speed

                Log.d("TESTI", "Lämpötila: ${temperature}C, kosteusprosentti: ${humidity}%, tuulen nopeus: ${windSpeed}m/s")

                binding.textViewCityWeatherTemp.text = "Lämpötila: " + temperature.toString() + "C"
                binding.textViewCityWeatherHumidity.text = "Kosteusprosentti: " + humidity.toString() + "%"
                binding.textViewCityWeatherWindSpeed.text = "Tuulen nopeus: " + windSpeed.toString() + "m/s"

            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("TESTI", it.toString())
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {

                // basic headers for the data
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }

        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        // if using this in an activity, use "this" instead of "context"
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}