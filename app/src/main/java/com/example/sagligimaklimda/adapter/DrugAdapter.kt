package com.example.sagligimaklimda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sagligimaklimda.DrugListFragmentDirections
import com.example.sagligimaklimda.databinding.DrugItemLayoutBinding
import com.example.sagligimaklimda.model.Drug
import java.text.SimpleDateFormat
import java.util.Locale

class DrugAdapter : RecyclerView.Adapter<DrugAdapter.DrugViewHolder>() {

    private var drugList = emptyList<Drug>()

    class DrugViewHolder(private val binding: DrugItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drug: Drug) {
            binding.itemTitle.text = drug.name
            val endDate = java.util.Date(drug.endDate)
            val dateFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(endDate)
            binding.itemSubtitle.text = "Günde "+drug.timesPerDay+" kere "+drug.description+", "+formattedDate+"'e kadar"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugViewHolder {
        val binding = DrugItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrugViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrugViewHolder, position: Int) {
        val currentDrug = drugList[position]
        holder.bind(currentDrug)
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(DrugListFragmentDirections.actionDrugListFragmentToFragmentUpdateDrug(currentDrug))
        }
    }

    override fun getItemCount() = drugList.size

    fun setDrugs(drugs: List<Drug>) {
        this.drugList = drugs
        notifyDataSetChanged()
    }
}