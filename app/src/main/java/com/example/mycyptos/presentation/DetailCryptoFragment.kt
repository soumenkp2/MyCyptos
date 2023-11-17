package com.example.mycyptos.presentation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.mycyptos.R
import com.example.mycyptos.databinding.FragmentDetailCryptoBinding
import com.example.mycyptos.presentation.topcrypto.TopCryptoViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailCryptoFragment : Fragment() {

    private lateinit var binding : FragmentDetailCryptoBinding
    private lateinit var id : String
    val viewModel: TopCryptoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TopCryptoViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailCryptoBinding.inflate(layoutInflater, container, false)

        viewModel.selectedCrypto.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                binding.tvCryptoName.text = it.symbol.toString()
                binding.tvCryptoFullname.text = it.name.toString()

                id = it.id.toString()

                val priceFloat = it.quote.USD.price.toFloat()
                val formattedValue = String.format("%.2f", priceFloat)
                binding.tvCryptoValue.text = "$$formattedValue"

                val priceChangeInt = it.quote.USD.percent_change_24h.toFloat()
                val formattedChangeValue = String.format("%.2f", priceChangeInt)
                binding.tvCryptoChangeValue.text = "$formattedChangeValue%"

                if(priceChangeInt>0) {
                    binding.tvCryptoChangeValue.setTextColor(Color.parseColor("#00AE07"))
                } else {
                    binding.tvCryptoChangeValue.setTextColor(Color.parseColor("#FFF44336"))
                }

                Picasso.get().load("https://s2.coinmarketcap.com/static/img/coins/64x64/${it.id}.png").into(binding.ivBtc)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFav.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
            var value = sharedPreferences?.getString("fav","")
            val editor = sharedPreferences?.edit()

            val mutableSet : MutableSet<String> = mutableSetOf()
            if (value != null) {
                var symbol = ""
                value.forEach { char ->
                    if(char.equals(',')) {
                        mutableSet.add(symbol)
                        symbol = ""
                    } else {
                        symbol += char
                    }
                }
            }
            mutableSet.add(binding.tvCryptoName.text.toString())
            value = ""
            mutableSet.forEach {
                value += it
                value += ','
            }

            editor?.putString("fav", value)
            editor?.apply()
            Toast.makeText(context,value,Toast.LENGTH_SHORT).show()
        }

    }



}

