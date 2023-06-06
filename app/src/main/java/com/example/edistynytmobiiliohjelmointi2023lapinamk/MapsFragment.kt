package com.example.edistynytmobiiliohjelmointi2023lapinamk

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edistynytmobiiliohjelmointi2023lapinamk.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

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
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        // rovaniemi
        val rovaniemi = LatLng(66.50247438013193, 25.7300978471244)
        googleMap.addMarker(MarkerOptions().position(rovaniemi).title("Rovaniemi"))

        // siirtää mapsin kameran tähän koordinaattiin, lisätty +Zoom -> pitää olla float
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rovaniemi, 15f))
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

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}