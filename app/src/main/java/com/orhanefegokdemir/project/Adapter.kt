package com.orhanefegokdemir.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orhanefegokdemir.project.syste.Supplement

class MyAdapter(private val itemList: List<Supplement>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.titleTextView.text = currentItem.title
        // Bind other data as needed
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}