package com.matsavx.firstfinal.interfaces.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.matsavx.firstfinal.R
import com.matsavx.firstfinal.data.Product
import com.matsavx.firstfinal.util.EspressoIdlingResource
import kotlinx.android.synthetic.main.layout_product_list_item.view.*

class ProductListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_list_item, parent, false)
        return ProductViewHolder(view, interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Product>) {
        EspressoIdlingResource.increment()
        val dataCommitCallback = Runnable {
            EspressoIdlingResource.decrement()
        }
        differ.submitList(list, dataCommitCallback)
    }

    class ProductViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Product) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            textViewProductName.text = item.productName
            Glide.with(itemView)
                .load(item.productImage)
                .into(imageViewProductImage)
            textViewPrice.text = "Цена: ${item.price.toString()} руб."
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
    }
}
