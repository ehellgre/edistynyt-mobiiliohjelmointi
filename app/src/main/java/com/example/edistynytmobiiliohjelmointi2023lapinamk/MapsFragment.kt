package com.example.edistynytmobiiliohjelmointi2023lapinamk

import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.edistynytmobiiliohjelmointi2023lapinamk.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentMapsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Tehdään luokan ylätasolle uusi jäsenmuuttuja -> gMap
    // Tallennetaan googleMap olio talteen, jotta sitä voi käyttää muaaltakin kuin callbackin sisällä
    private lateinit var gMap : GoogleMap


    private val callback = OnMapReadyCallback { googleMap ->

        // laitetaan googleMap olio talteen
        gMap = googleMap

        // käynnistyy kun kartta avataan
        val sydney = LatLng(-34.0, 151.0)
        var m1 = googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        m1?.tag = "Sydney"

        // rovaniemi
        val rovaniemi = LatLng(66.50247438013193, 25.7300978471244)
        var m2 = googleMap.addMarker(MarkerOptions().position(rovaniemi).title("Rovaniemi"))
        m2?.tag = "Rovaniemi"

        // H2 lisätehtävä, piirretään viiva Rovaniemistä -> Sydneyyn ja ympyröidään Rollo
        // viiva rovaniemestä -> sydney
        val drawLine = PolylineOptions().apply {
            color(Color.RED)
            width(12f)
            add(rovaniemi, sydney)
        }
        googleMap.addPolyline(drawLine)

        // ympyröidään Rovaniemi
        val rvnCircle = CircleOptions().apply {
            center(rovaniemi)
            radius(2000.0)
        }
        googleMap.addCircle(rvnCircle)


        // siirtää mapsin kameran tähän koordinaattiin, lisätty +Zoom -> pitää olla float
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rovaniemi, 15f))

        googleMap.setOnMarkerClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //käytetään binding layeria karttafragmentissä

        // asetetaan zoom kontrollit päälle/pois Checkboxin arvon (boolean) perusteella
        binding.checkBoxZoomControls.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("TESTI", "CHECKED! " + isChecked.toString())


            gMap.uiSettings.isZoomControlsEnabled = isChecked
        }

        binding.radioButtonMapNormal.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isChecked) {
                gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }

        binding.radioButtonMapHybrid.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isChecked) {
                gMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
        }

        binding.radioButtonMapTerrain.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isChecked) {
                gMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
        }


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    // funktio vastaa siitä kun jotakin markeria klikataan kartalla
    override fun onMarkerClick(p0: Marker): Boolean {
        Log.d("TESTI", "Marker CLICK")
        Log.d("TESTI", p0.tag.toString())

        val lat = p0.position.latitude.toFloat()
        val lon = p0.position.longitude.toFloat()

        val action = MapsFragmentDirections.actionMapsFragmentToCityWeatherFragment(lat, lon)
        findNavController().navigate(action)

        return false
    }
}