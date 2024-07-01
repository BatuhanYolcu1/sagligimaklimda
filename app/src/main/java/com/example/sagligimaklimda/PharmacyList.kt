package com.example.sagligimaklimda

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sagligimaklimda.adapter.PharmacyAdapter
import com.example.sagligimaklimda.databinding.FragmentPharmacyListBinding
import com.example.sagligimaklimda.viewmodel.PharmacyViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class PharmacyList : Fragment() {
    private lateinit var binding: FragmentPharmacyListBinding
    private lateinit var adapter: PharmacyAdapter
    private val pharmacyViewModel: PharmacyViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    Log.d("LocationInfo", "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
                    // pharmacyViewModel.updateLocation(it.latitude, it.longitude)

                } ?: Log.d("LocationInfo", "Konum bilgisi alınamadı.")
            }
        } else {
            // Kullanıcıdan konum izni iste
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
        }
        pharmacyViewModel.updateLocation(38.353777, 38.341523)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPharmacyListBinding.inflate(inflater, container, false)

        binding.phRecyclerView.layoutManager = LinearLayoutManager(context)


        adapter = PharmacyAdapter()
        binding.phRecyclerView.adapter = adapter




        observeData()




        return binding.root
    }


    private fun observeData() {
        pharmacyViewModel.pharmacyValue.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.update(it)
                if (it.isEmpty()) {
                    Toast.makeText(context, "Henüz Kayıt Yok", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}


