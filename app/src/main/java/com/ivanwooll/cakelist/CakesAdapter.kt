package com.ivanwooll.cakelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.ivanwooll.cakelist.data.room.Cake
import kotlinx.android.synthetic.main.list_item_cake.view.*

class CakesAdapter(private val callback: Callback) : RecyclerView.Adapter<CakesAdapter.VH>() {

    interface Callback {
        fun onItemClick(description: String)
    }

    private val items = arrayListOf<Cake>()

    fun updateItems(list: List<Cake>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_cake,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (desc, image, title) = items[holder.adapterPosition]
        holder.itemView.apply {
            textView.text = title
            imageView.load(image) {
                error(R.drawable.ic_error_24dp)
            }
            setOnClickListener { callback.onItemClick(desc) }
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
}