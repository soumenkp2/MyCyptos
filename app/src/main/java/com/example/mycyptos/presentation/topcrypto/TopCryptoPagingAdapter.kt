package com.example.mycyptos.presentation.topcrypto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mycyptos.databinding.ItemCryptoBinding
import com.example.mycyptos.datamodels.Data


class TopCryptoPagingAdapter() : PagingDataAdapter<Data,TopCryptoPagingAdapter.CryptoViewHolder>(CryptoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCryptoBinding.inflate(inflater, parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val message = getItem(position)
        message?.let {
            holder.binding.apply {
                tvCryptoName.text = it.name.toString()
            }
        }
    }

    class CryptoViewHolder(val binding: ItemCryptoBinding) : RecyclerView.ViewHolder(binding.root) {}

    // DiffCallback to efficiently update items in the RecyclerView
    private class CryptoDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id // Use a unique identifier for items
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem // Check if the items have the same content
        }
    }

}



