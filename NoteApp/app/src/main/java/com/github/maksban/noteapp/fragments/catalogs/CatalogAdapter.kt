package com.github.maksban.noteapp.fragments.catalogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.maksban.noteapp.data.model.Catalog
import com.github.maksban.noteapp.databinding.RowCatalogBinding

class CatalogAdapter(private val onItemClicked: (Catalog) -> Unit) :
    ListAdapter<Catalog, CatalogAdapter.CatalogViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val viewHolder = CatalogViewHolder(
            RowCatalogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CatalogViewHolder(private var binding: RowCatalogBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(catalog: Catalog){
                binding.tvName.text = catalog.title
            }

    }

    fun getByPosition(position: Int): Catalog = getItem(position)

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Catalog>() {
            override fun areItemsTheSame(oldItem: Catalog, newItem: Catalog): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Catalog, newItem: Catalog): Boolean {
                return oldItem == newItem
            }

        }

    }


}



