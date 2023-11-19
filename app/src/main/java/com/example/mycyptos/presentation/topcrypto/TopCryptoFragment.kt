package com.example.mycyptos.presentation.topcrypto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycyptos.R
import com.example.mycyptos.databinding.FragmentTopCryptoBinding
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.utils.AppConstants
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TopCryptoFragment : Fragment() {

    //private val viewModel: TopCryptoViewModel by viewModels()
    val viewModel: TopCryptoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TopCryptoViewModel::class.java) }

    private lateinit var binding: FragmentTopCryptoBinding
    private lateinit var snackBarExitConfirmation: Snackbar
    private lateinit var pagingAdapter: TopCryptoPagingAdapter
    private var mList : MutableList<Data> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopCryptoBinding.inflate(layoutInflater, container, false)

        pagingAdapter = TopCryptoPagingAdapter(object : ItemClickListener {
            override fun onItemClick(data: Data) {
                viewModel.selectedCrypto.postValue(data)
                val action = TopCryptoFragmentDirections.actionTopCryptoFragmentToDetailCryptoFragment()
                findNavController().navigate(action)
            }
        })
        viewModel.getTopCryptoData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val snackbar = AppConstants.snackBarTemplate(binding.root,"Fetching Data....")
        snackbar.show()

        viewModel.pagingData.observe(viewLifecycleOwner, Observer {
            binding.rvTopCrypto.layoutManager = LinearLayoutManager(context)
            Log.d("api ui pageLoaded",it.toString())
            pagingAdapter.submitData(lifecycle,it)
            binding.rvTopCrypto.adapter = pagingAdapter
            pagingAdapter.notifyDataSetChanged()

            viewModel.getTopRankedCryptoData()

            if(viewModel.pagingData.value!=null) {
                Log.d("api ui dataLoaded",viewModel.pagingData.value.toString() + viewModel._dataLoaded.value)
                viewModel._dataLoaded.postValue(true)
            }
            snackbar.dismiss()
        })

        viewModel._dataLoaded.observe(viewLifecycleOwner, Observer {
            if(it) {
                viewModel.getTopRankedCryptoData()
            }
        })

        viewModel.topRankedCryptoData.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                binding.tvCryptoName.text = it.symbol.toString()
                binding.tvCryptoFullname.text = it.name.toString()
            } else {

            }
        })

        viewModel.getCryptoListData()
        viewModel.cryptoListData.observe(viewLifecycleOwner, Observer{
            it.forEach {
                mList.add(it)
            }
            setBannerData(mList[0])
        })

        binding.etSearch.setOnClickListener {
            val action = TopCryptoFragmentDirections.actionTopCryptoFragmentToSearchCryptoFragment()
            findNavController().navigate(action)
        }

        setUpBackPress()
    }

    private fun setBannerData(data : Data) {
        binding.tvCryptoName.text = data.symbol.toString()
        binding.tvCryptoFullname.text = data.name.toString()

        val priceFloat = data.quote.USD.price.toFloat()
        val formattedValue = String.format("%.2f", priceFloat)
        binding.tvCryptoValue.text = "$$formattedValue"

        val priceChangeInt = data.quote.USD.percent_change_24h.toFloat()
        val formattedChangeValue = String.format("%.2f", priceChangeInt)
        binding.tvCryptoChangeValue.text = "$formattedChangeValue%"

        Picasso.get().load("https://s2.coinmarketcap.com/static/img/coins/64x64/${data.id}.png").into(binding.ivBtc)

    }

    private fun setUpBackPress() {
        snackBarExitConfirmation = Snackbar.make(
            requireActivity().window.decorView.rootView,
            "Please press BACK again to exit",
            Snackbar.LENGTH_LONG
        )

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!snackBarExitConfirmation.isShown) {
                    snackBarExitConfirmation.show()
                } else {
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}