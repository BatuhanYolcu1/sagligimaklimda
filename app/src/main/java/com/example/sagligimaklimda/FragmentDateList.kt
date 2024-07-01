package com.example.sagligimaklimda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sagligimaklimda.adapter.DateAdapter
import com.example.sagligimaklimda.databinding.FragmentDateListBinding
import com.example.sagligimaklimda.viewmodel.DateViewModel


class FragmentDateList : Fragment() {
    private lateinit var binding: FragmentDateListBinding
    private lateinit var adapter: DateAdapter
    private val dateViewModel: DateViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_fragmentDateList_to_fragmentHome)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDateListBinding.inflate(inflater,container,false)
        binding.recyclerView.layoutManager=LinearLayoutManager(context)

        adapter = DateAdapter()
        binding.recyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentDateList_to_fragmentSetDate)
        }

        observeData()
        return binding.root
    }

    fun observeData(){
        dateViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setDates(it)
                if(it.isEmpty()){
                    Toast.makeText(context,"Henüz Kayıt Yok", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


}