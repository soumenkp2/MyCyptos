package com.example.mycyptos.presentation.topcrypto

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycyptos.R
import com.example.mycyptos.databinding.FragmentTopCryptoBinding
import com.example.mycyptos.utils.AppConstants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopCryptoFragment : Fragment() {

    private val viewModel: TopCryptoViewModel by viewModels()
    private lateinit var binding: FragmentTopCryptoBinding
    private lateinit var snackBarExitConfirmation: Snackbar
    private lateinit var pagingAdapter: TopCryptoPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopCryptoBinding.inflate(layoutInflater, container, false)

        pagingAdapter = TopCryptoPagingAdapter()
        viewModel.getTopCryptoData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val snackbar = AppConstants.snackBarTemplate(binding.root,"Fetching Data....")
        snackbar.show()

        viewModel.pagingData.observe(viewLifecycleOwner, Observer {
            binding.rvTopCrypto.layoutManager = LinearLayoutManager(context)
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

        setUpBackPress()
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