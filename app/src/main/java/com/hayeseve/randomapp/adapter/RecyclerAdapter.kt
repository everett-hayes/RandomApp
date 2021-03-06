package com.hayeseve.randomapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hayeseve.randomapp.activity.MainActivity
import com.hayeseve.randomapp.databinding.AdapterItemBinding
import com.hayeseve.randomapp.model.RandomAddress
import com.hayeseve.randomapp.model.RandomCrypto
import com.hayeseve.randomapp.model.RandomDessert
import com.hayeseve.randomapp.model.RandomHipster
import com.hayeseve.randomapp.model.RandomUser.RandomUser

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
                RandomUser::class -> (context as MainActivity).goToUser(false, currentItem as RandomUser);
                RandomCrypto::class -> (context as MainActivity).goToCrypto(false, currentItem as RandomCrypto);
                RandomDessert::class -> (context as MainActivity).goToDessert(false, currentItem as RandomDessert);
                RandomHipster::class -> (context as MainActivity).goToHipster(false, currentItem as RandomHipster);
            }
        }

        holder.itemBinding.btnDelete.setOnClickListener {
            deleteItem(position);
        }
    }

    fun addItem(a: Any) {
        Log.d("debug", a::class.toString());
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
            when (a::class) {
                RandomAddress::class -> {
                    val address = a as RandomAddress
                    itemBinding.classType.setText("Random Address");
                    itemBinding.summary.setText(address.streetAddress);
                }
                RandomUser::class -> {
                    val user = a as RandomUser
                    itemBinding.classType.setText("Random User");
                    itemBinding.summary.setText(user.username);
                }
                RandomCrypto::class -> {
                    val crypto = a as RandomCrypto
                    itemBinding.classType.setText("Random Crypto");
                    itemBinding.summary.setText("${crypto.sha1?.substring(0, 20)} ...");
                }
                RandomDessert::class -> {
                    val dessert = a as RandomDessert
                    itemBinding.classType.setText("Random Dessert");
                    itemBinding.summary.setText(dessert.variety);
                }
                RandomHipster::class -> {
                    val hipster = a as RandomHipster
                    itemBinding.classType.setText("Random Hipster");
                    itemBinding.summary.setText(hipster.word);
                }
            }
        }
    }
}