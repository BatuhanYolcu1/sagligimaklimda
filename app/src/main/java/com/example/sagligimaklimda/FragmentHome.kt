package com.example.sagligimaklimda

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sagligimaklimda.databinding.FragmentHomeBinding
import java.util.Calendar


class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeNesnesi = this
        greetingTitle()
        sharedPreferences =
            requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val bloodcel = sharedPreferences.getString("blood_cell", "kan grubu")
        binding.bloodGroup.text = bloodcel
        val emergencyCall = sharedPreferences.getString("emergency_number", "acil durumu araması")
        binding.emerNumber.text = emergencyCall
        val userName = sharedPreferences.getString("user_name", "Kullanıcı Adı")
        binding.userName.setText(userName).toString().uppercase()
        binding.drugList.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_drugListFragment)
        }
        binding.phList.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_pharmacyList)
        }
        binding.accountEdit.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_userInfoEdit)
        }
        binding.dateList.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentDateList)
        }
        binding.cardCall.setOnClickListener {
            if (emergencyCall!!.isNotEmpty()) {
                startPhoneCall(emergencyCall)
            }
        }
        return binding.root
    }

    private fun startPhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun greetingTitle() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        if (hour in 19..23 || hour in 0..5) {
            binding.textView3.text = "İYİ AKŞAMLAR"
        } else {
            binding.textView3.text = "İYİ GÜNLER"
        }
    }
}