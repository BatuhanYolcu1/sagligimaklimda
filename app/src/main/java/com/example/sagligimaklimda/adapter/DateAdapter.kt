package com.example.sagligimaklimda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sagligimaklimda.FragmentDateListDirections
import com.example.sagligimaklimda.R
import com.example.sagligimaklimda.databinding.PharmacyItemLayoutBinding
import com.example.sagligimaklimda.model.DateModel
import java.text.SimpleDateFormat
import java.util.Locale

class DateAdapter : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var dateList = emptyList<DateModel>()

    class DateViewHolder(private val binding: PharmacyItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: DateModel) {
            binding.itemTitle.text = date.Hospital
            binding.itemIcon.setImageResource(R.drawable.baseline_local_hospital_24)

            val dateFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
            val dateFormatted = dateFormat.format(date.Date)

            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val timeFormatted = timeFormat.format(date.time)

            binding.itemTitle.text = date.Hospital
            binding.itemSubtitle.text = "${dateFormatted} ${timeFormatted} tarihinde doktor ${date.Doctor} ile randevunuz var"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = PharmacyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val currentDate = dateList[position]
        holder.bind(currentDate)
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(FragmentDateListDirections.actionFragmentDateListToFragmentUpdateDate(currentDate))
        }
    }

    override fun getItemCount() = dateList.size

    fun setDates(dates: List<DateModel>) {
        this.dateList = dates
        notifyDataSetChanged()
    }
}
