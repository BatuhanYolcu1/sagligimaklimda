package com.example.sagligimaklimda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sagligimaklimda.databinding.FragmentUpdateDateBinding
import com.example.sagligimaklimda.model.DateModel
import com.example.sagligimaklimda.viewmodel.DateViewModel

import java.util.Calendar


class FragmentUpdateDate : Fragment() {
    private lateinit var binding: FragmentUpdateDateBinding
    private val dateViewModel: DateViewModel by viewModels()
    private val args by navArgs<FragmentUpdateDateArgs>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_fragmentUpdateDate_to_fragmentDateList)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateDateBinding.inflate(inflater, container, false)

        binding.docNameUpdate.setText(args.currentDate.Doctor)
        binding.hospitalUpdate.setText(args.currentDate.Hospital)

        val calendarForTime = Calendar.getInstance().apply {
            timeInMillis = args.currentDate.time
        }

        binding.timeInputUpdate.hour = calendarForTime.get(Calendar.HOUR_OF_DAY)
        binding.timeInputUpdate.minute = calendarForTime.get(Calendar.MINUTE)

        val calendarForDate = Calendar.getInstance().apply {
            timeInMillis = args.currentDate.Date
        }
        binding.endDatePickerUpdate.updateDate(
            calendarForDate.get(Calendar.YEAR),
            calendarForDate.get(Calendar.MONTH),
            calendarForDate.get(Calendar.DAY_OF_MONTH)
        )


        binding.updateButton.setOnClickListener {

           buttonUpdate()
        }

        setHasOptionsMenu(true)
        return binding.root
    }


    fun buttonUpdate(){
        val hour = binding.timeInputUpdate.hour
        val minute = binding.timeInputUpdate.minute
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        val time = calendar.timeInMillis
        val endDateCalendar = Calendar.getInstance()
        endDateCalendar.set(
            binding.endDatePickerUpdate.year,
            binding.endDatePickerUpdate.month,
            binding.endDatePickerUpdate.dayOfMonth
        )
        val endDate = endDateCalendar.timeInMillis

        val updateDate =DateModel(args.currentDate.id,endDate,binding.docNameUpdate.text.toString(),binding.hospitalUpdate.text.toString(),time)
        dateViewModel.update(updateDate)
        findNavController().navigate(R.id.action_fragmentUpdateDate_to_fragmentDateList)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_tool, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteall)
        { dateViewModel.delete(args.currentDate)
            findNavController().navigate(R.id.action_fragmentUpdateDate_to_fragmentDateList)
        }
        return super.onOptionsItemSelected(item)

    }


}