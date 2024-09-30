package com.example.tarea1grupog3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.tarea1grupog3.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class FragmentLugares : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var spinner: Spinner
    private val lugares = listOf(
        "Estadio Alberto Gallardo", // Ejemplo de lugar
        "Estadio Alejandro Villanueva",
        "Estadio Monumental",
        "Estadio Nacional",
        "Estadio UNMSM"
    )

    private val coordenadas = mapOf(
        "Estadio Alberto Gallardo" to LatLng(-12.037929, -77.045074),
        "Estadio Alejandro Villanueva" to LatLng(-12.068362, -77.022061),
        "Estadio Monumental" to LatLng(-12.055559, -76.937087),
        "Estadio Nacional" to LatLng(-12.067242, -77.033635),
        "Estadio UNMSM" to LatLng(-12.057407, -77.083410)
    )

    private var currentMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lugares, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Configurar el Spinner
        spinner = view.findViewById(R.id.spinnerLugares)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lugares)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Listener para el Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val lugarSeleccionado = lugares[position]
                val coordenada = coordenadas[lugarSeleccionado]
                coordenada?.let {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                    addMarker(it, lugarSeleccionado)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    // Método para añadir el marcador en el mapa
    private fun addMarker(location: LatLng, title: String) {
        currentMarker?.remove() // Eliminar el marcador actual si existe
        currentMarker = mMap.addMarker(MarkerOptions().position(location).title(title))
    }
}
