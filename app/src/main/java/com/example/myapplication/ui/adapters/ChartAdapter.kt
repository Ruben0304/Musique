package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class ChartAdapter(private val items: List<ChartItem>) : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chart_item, parent, false)
        return ChartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    class ChartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rankTextView: TextView = itemView.findViewById(R.id.textView8)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView13)
        private val nameTextView: TextView = itemView.findViewById(R.id.textView9)

        fun bind(item: ChartItem) {
            rankTextView.text = item.rank.toString()
            nameTextView.text = item.name
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(imageView)
        }
    }
}


data class ChartItem(
    val rank: Int,
    val imageUrl: String,
    val name: String
)
