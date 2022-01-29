package com.calendar.base.adapter.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.calendar.R
import com.calendar.base.model.MonthItem

class MonthAdapter : ListAdapter<MonthItem, MonthViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_month,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffUtilCallBack : DiffUtil.ItemCallback<MonthItem>() {
        override fun areItemsTheSame(oldItem: MonthItem, newItem: MonthItem): Boolean {
            return oldItem.month == newItem.month
        }

        override fun areContentsTheSame(oldItem: MonthItem, newItem: MonthItem): Boolean {
            return oldItem == newItem
        }
    }
}