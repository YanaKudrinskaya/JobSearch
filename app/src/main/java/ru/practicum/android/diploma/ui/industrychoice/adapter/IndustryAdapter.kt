package ru.practicum.android.diploma.ui.industrychoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.industrychoice.models.Industry

class IndustryAdapter(
    private val onClick: (Industry) -> Unit,
    private var selectedId: Int? = null
) : RecyclerView.Adapter<IndustryViewHolder>() {

    private var items: MutableList<Industry> = mutableListOf()
    private val originalList: MutableList<Industry> = mutableListOf()
    private val filteredList: MutableList<Industry> = mutableListOf()

    fun getItems(): List<Industry> {
        return items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = IndustryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, isItemSelected(item), onClick)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: MutableList<Industry>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()

        originalList.clear()
        originalList.addAll(newItems)
    }

    private fun updateDisplayList(updatedList: List<Industry>) {
        items.clear()
        items.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun filter(query: String?) {
        filteredList.clear()
        if (query.isNullOrEmpty()) {
            updateDisplayList(originalList)
        } else {
            for (item in originalList) {
                if (item.name.contains(query, ignoreCase = true)) {
                    filteredList.add(item)
                }
            }
            updateDisplayList(filteredList)
        }
    }
    fun updateSelection(newSelectedId: Int?) {
        selectedId = newSelectedId
        notifyDataSetChanged()
    }
    fun isItemSelected(item: Industry): Boolean {
        return item.id == selectedId
    }
}
