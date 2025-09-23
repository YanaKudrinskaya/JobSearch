package ru.practicum.android.diploma.ui.industrychoice.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.industrychoice.models.Industry

class IndustryViewHolder(
    private val binding: IndustryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Industry, isSelected: Boolean, onClick: (Industry) -> Unit) {
        binding.industryText.text = item.name
        binding.industryButton.isChecked = isSelected

        binding.root.setOnClickListener { onClick(item) }
        binding.industryButton.setOnClickListener { onClick(item) }
    }
}
