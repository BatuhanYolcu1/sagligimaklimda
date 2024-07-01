package com.example.sagligimaklimda

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sagligimaklimda.databinding.FragmentUserInfoEditBinding


class UserInfoEdit : Fragment() {
    private lateinit var binding: FragmentUserInfoEditBinding
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoEditBinding.inflate(inflater,container,false)

        sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "isim")
        val bloodCell=sharedPreferences.getString("blood_cell","kan grubu")
        val myNumber=sharedPreferences.getString("my_number","numara")
        val emergencyNumber=sharedPreferences.getString("emergency_number","acil durum çağrısı")
        val myAdress=sharedPreferences.getString("my_adress","adres")

        binding.editTextName.setText(userName)
        binding.editTextKanGrubu.setText(bloodCell)
        binding.myPhoneNumber.setText(myNumber)
        binding.emerCall.setText(emergencyNumber)
        binding.myAdress.setText(myAdress)





        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            val userName = binding.editTextName.text.toString()
            val bloodCell = binding.editTextKanGrubu.text.toString()
            val myNumber = binding.myPhoneNumber.text.toString()
            val emerNumber = binding.emerCall.text.toString()
            val myAdress = binding.myAdress.text.toString()
            saveUserInfoToSharedPreferences(userName,bloodCell,myAdress, myNumber, emerNumber)
            findNavController().navigate(R.id.action_userInfoEdit_to_fragmentHome)
            return true

        }
        return super.onOptionsItemSelected(item)
    }


    private fun saveUserInfoToSharedPreferences(userName: String,bloodCell: String,myAdress: String,myNumber: String,emerNumber: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_name", userName)
        editor.putString("blood_cell",bloodCell )
        editor.putString("my_number", myNumber)
        editor.putString("emergency_number", emerNumber)
        editor.putString("my_adress", myAdress)
        editor.apply()
    }
}