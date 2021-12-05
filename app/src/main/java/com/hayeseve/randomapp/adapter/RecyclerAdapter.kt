package com.hayeseve.randomapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hayeseve.randomapp.databinding.AdapterItemBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    val context: Context
    var items = mutableListOf<Object>()

    constructor(context: Context) : super() {
        this.context = context
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemRowBinding = AdapterItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(listItemRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[holder.adapterPosition]
        holder.bind(currentItem)
    }

    fun addItem(o: Object) {
        items.add(o)
        notifyItemInserted(items.lastIndex)
    }

    fun deleteItem(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged();
    }

    inner class ViewHolder(val itemBinding: AdapterItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(o: Object) {
            // fill this out
        }
    }
}