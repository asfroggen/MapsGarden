package com.esaudev.mapsplayground

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.esaudev.mapsplayground.databinding.ItemLocationBinding

class LocationListAdapter: ListAdapter<Location, BaseListViewHolder<*>>(DiffUtilCallback) {

    private object DiffUtilCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindViewHolderList(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*>, position: Int) {
        when (holder) {
            is BindViewHolderList -> holder.bind(getItem(position), position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemLocationBinding) : BaseListViewHolder<Location>(binding.root) {

        override fun bind(item: Location, position: Int) = with(binding) {
            locationTitle.text = item.title
            locationCoordinates.text = item.coordinates.toString()
        }
    }

}