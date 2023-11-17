package com.example.mycyptos.presentation.favcrypto

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycyptos.R
import com.example.mycyptos.databinding.ActivityMainBinding.inflate
import com.example.mycyptos.databinding.FragmentFavCryptoBinding
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.presentation.topcrypto.ItemClickListener
import com.example.mycyptos.presentation.topcrypto.TopCryptoFragmentDirections
import com.example.mycyptos.presentation.topcrypto.TopCryptoPagingAdapter
import com.example.mycyptos.presentation.topcrypto.TopCryptoViewModel

class FavCryptoFragment : Fragment() {

    private lateinit var binding: FragmentFavCryptoBinding
    private lateinit var pagingAdapter: TopCryptoPagingAdapter
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
        binding = FragmentFavCryptoBinding.inflate(inflater, container,false)

        val sharedPreferences = context?.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        val value = sharedPreferences?.getString("fav","")

        pagingAdapter = value?.let {
            TopCryptoPagingAdapter(object : ItemClickListener {
                override fun onItemClick(data: Data) {}
            }, it)
        }!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pagingData.observe(viewLifecycleOwner, Observer {
            binding.rvTopCrypto.layoutManager = LinearLayoutManager(context)
            pagingAdapter.submitData(lifecycle,it)
            binding.rvTopCrypto.adapter = pagingAdapter
            pagingAdapter.notifyDataSetChanged()
        })
    }

}