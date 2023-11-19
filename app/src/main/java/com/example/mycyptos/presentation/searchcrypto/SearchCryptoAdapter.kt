package com.example.mycyptos.presentation.searchcrypto

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycyptos.R
import com.example.mycyptos.databinding.ItemCryptoBinding
import com.example.mycyptos.datamodels.Data
import com.squareup.picasso.Picasso


class SearchCryptoAdapter(var dataList: List<Data>) :
    RecyclerView.Adapter<SearchCryptoAdapter.DataViewHolder>() {

    class DataViewHolder(val binding: ItemCryptoBinding) : RecyclerView.ViewHolder(binding.root) {}

    fun setFilteredList(mList: List<Data>){
        this.dataList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCryptoBinding.inflate(inflater, parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = dataList.get(position)

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

            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}