package com.example.mycyptos.presentation.searchcrypto

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycyptos.R
import com.example.mycyptos.databinding.FragmentSearchCryptoBinding
import com.example.mycyptos.datamodels.Data
import com.example.mycyptos.presentation.topcrypto.TopCryptoViewModel
import java.util.Locale


class SearchCryptoFragment : Fragment() {

    val viewModel: TopCryptoViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TopCryptoViewModel::class.java) }

    private lateinit var binding : FragmentSearchCryptoBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private var mList = ArrayList<Data>()
    private lateinit var adapter: SearchCryptoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchCryptoBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        searchView = binding.searchView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchCryptoAdapter(mList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        viewModel.getCryptoListData()
        viewModel.cryptoListData.observe(viewLifecycleOwner, Observer{
            it.forEach {
                mList.add(it)
            }
        })

        return binding.root
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Data>()
            for (i in mList) {
                if (i.name.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                //Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }




}