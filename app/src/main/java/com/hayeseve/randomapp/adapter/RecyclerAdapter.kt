package com.hayeseve.randomapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hayeseve.randomapp.activity.MainActivity
import com.hayeseve.randomapp.databinding.AdapterItemBinding
import com.hayeseve.randomapp.model.RandomAddress

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    val context: Context
    var items = mutableListOf<Any>()

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

        holder.itemBinding.btnView.setOnClickListener {
            when (currentItem::class)  {
                RandomAddress::class -> (context as MainActivity).goToAddress(false, currentItem as RandomAddress);
            }
        }
    }

    fun addItem(a: Any) {
        items.add(a)
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
        fun bind(a: Any) {

            if (a is RandomAddress) {
                val address = a as RandomAddress
                itemBinding.classType.setText("Random Address");
                itemBinding.summary.setText(address.streetAddress);
            }
        }
    }
}