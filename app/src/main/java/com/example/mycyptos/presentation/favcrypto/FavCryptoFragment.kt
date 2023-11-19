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

/**
 * Fragment displaying a paginated list of user's favorite cryptocurrencies.
 * Utilizes the FavCryptoViewModel for data retrieval and updates.
 *
 * Properties:
 * - binding: View binding for the fragment layout.
 * - pagingAdapter: Adapter for displaying paginated favorite cryptocurrency data.
 * - viewModel: Lazy-initialized instance of FavCryptoViewModel for handling favorite cryptocurrency data.
 *
 * Methods:
 * - onCreateView: Inflates the fragment layout, initializes the paging adapter, and triggers the API call to get favorite cryptocurrency data.
 * - onViewCreated: Observes changes in the paging data from the ViewModel and updates the RecyclerView accordingly.
 *   Sets up the RecyclerView layout manager, submits the new paging data to the adapter, associates it with the fragment's lifecycle, sets the RecyclerView adapter to the updated paging adapter, and notifies the adapter of any data set changes to reflect updates in the UI.
 */
class FavCryptoFragment : Fragment() {

    private lateinit var binding: FragmentFavCryptoBinding
    private lateinit var pagingAdapter: FavCryptoPagingAdapter
    val viewModel: FavCryptoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(FavCryptoViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavCryptoBinding.inflate(inflater, container,false)

        pagingAdapter = FavCryptoPagingAdapter(object : ItemClickListener {
            override fun onItemClick(data: Data) {

            }
        })
        viewModel.getFavCryptoData()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Observes changes in the paging data from the ViewModel and updates the RecyclerView accordingly.
         * - Sets up the RecyclerView layout manager using LinearLayoutManager.
         * - Submits the new paging data to the adapter and associates it with the fragment's lifecycle.
         * - Sets the RecyclerView adapter to the updated paging adapter.
         * - Notifies the adapter of any data set changes to reflect updates in the UI.
         */
        viewModel.pagingData.observe(viewLifecycleOwner, Observer {
            binding.rvTopCrypto.layoutManager = LinearLayoutManager(context)
            pagingAdapter.submitData(lifecycle,it)
            binding.rvTopCrypto.adapter = pagingAdapter
            pagingAdapter.notifyDataSetChanged()
        })
    }

}