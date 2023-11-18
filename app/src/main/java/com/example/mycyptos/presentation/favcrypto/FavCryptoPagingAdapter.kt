package com.example.mycyptos.presentation.favcrypto

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mycyptos.R
import com.example.mycyptos.databinding.ItemCryptoBinding
import com.example.mycyptos.datamodels.Data
import com.squareup.picasso.Picasso

class FavCryptoPagingAdapter(private val listener: ItemClickListener) : PagingDataAdapter<Data, FavCryptoPagingAdapter.CryptoViewHolder>(CryptoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCryptoBinding.inflate(inflater, parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val data = getItem(position)

        data?.let {
            holder.binding.apply {
                tvCryptoName.text = it.symbol.toString()
                tvCryptoFullname.text = it.name.toString()

                val priceFloat = it.quote.USD.price.toFloat()
                val formattedValue = String.format("%.2f", priceFloat)
                tvCryptoValue.text = "$$formattedValue"

                val priceChangeInt = it.quote.USD.percent_change_24h.toFloat()
                val formattedChangeValue = String.format("%.2f", priceChangeInt)
                tvChange.text = "$formattedChangeValue%"

                if(priceChangeInt>0) {
                    holder.binding.tvChange.setTextColor(Color.parseColor("#00AE07"))
                    holder.binding.ivGraph.setImageResource(R.drawable.green_inc)
                } else {
                    holder.binding.tvChange.setTextColor(Color.parseColor("#FFF44336"))
                    holder.binding.ivGraph.setImageResource(R.drawable.reddec)
                }

                Picasso.get().load("https://s2.coinmarketcap.com/static/img/coins/64x64/${it.id}.png").into(holder.binding.ivBtc)
                holder.binding.itemParent.setOnClickListener {
                    listener.onItemClick(data)
                }
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

interface ItemClickListener {
    fun onItemClick(data : Data)
}



