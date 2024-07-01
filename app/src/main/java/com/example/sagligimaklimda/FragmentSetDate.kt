package com.example.sagligimaklimda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sagligimaklimda.databinding.FragmentSetDateBinding
import com.example.sagligimaklimda.model.DateModel
import com.example.sagligimaklimda.viewmodel.DateViewModel
import java.util.Calendar


class FragmentSetDate : Fragment() {
    private lateinit var binding: FragmentSetDateBinding
    private val dateViewModel: DateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_fragmentSetDate_to_fragmentDateList)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetDateBinding.inflate(inflater,container,false)
        binding.addButton.setOnClickListener {
            buttonSave()
        }
        return binding.root
    }
    fun buttonSave(){
        val hour = binding.timeInput.hour
        val minute = binding.timeInput.minute
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        val time = calendar.timeInMillis
        val endDateCalendar = Calendar.getInstance()
        endDateCalendar.set(
            binding.endDatePicker.year,
            binding.endDatePicker.month,
            binding.endDatePicker.dayOfMonth
        )
        val docDate = endDateCalendar.timeInMillis
        val dateModel=DateModel(0,docDate,binding.docName.text.toString(),binding.hospitalName.text.toString(),time)
        dateViewModel.impDate(dateModel)

        findNavController().navigate(R.id.action_fragmentSetDate_to_fragmentDateList)
    }



}