package com.example.mycyptos.presentation.detailCrypto

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mycyptos.R
import com.example.mycyptos.databinding.FragmentDetailCryptoBinding
import com.example.mycyptos.presentation.topcrypto.TopCryptoFragment
import com.example.mycyptos.presentation.topcrypto.TopCryptoViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment displaying detailed information about a selected cryptocurrency.
 * Utilizes the TopCryptoViewModel for managing selected cryptocurrency data.
 *
 * Properties:
 * - binding: View binding for the fragment layout.
 * - id: String to store the selected cryptocurrency's ID.
 * - viewModel: Lazy-initialized instance of TopCryptoViewModel for handling cryptocurrency data.
 *
 * Methods:
 * - onCreateView: Inflates the fragment layout, initializes observers, and sets up back press handling.
 * - initObservers: Observes changes in the selected cryptocurrency data and updates the UI accordingly.
 * - onViewCreated: Sets up the binding for click events on the favorite button.
 *   Retrieves and updates the list of favorite cryptocurrencies in SharedPreferences.
 * - setBinding: Handles the click event on the favorite button.
 *   Retrieves SharedPreferences for storing favorite cryptocurrency symbols.
 *   Retrieves the existing comma-separated string of favorite symbols from SharedPreferences.
 *   Initializes a mutable set to hold individual favorite symbols.
 *   Parses the existing string of favorite symbols: Splits the string at each comma and adds symbols to the set.
 *   Adds the currently selected cryptocurrency's symbol to the set of favorites.
 *   Resets the value string for constructing the updated favorite symbols string.
 *   Iterates through the set and reconstructs the comma-separated string of favorite symbols.
 *   Stores the updated favorite symbols string in SharedPreferences.
 *   Displays a toast indicating that the cryptocurrency has been selected as a favorite.
 * - setUpBackPress: Sets up back press handling to navigate back to the TopCryptoFragment.
 */
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
        initObservers()
        setUpBackPress()
        return binding.root
    }

    private fun initObservers() {
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
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
    }

    private fun setBinding() {

        // Handle click event on the favorite button: Retrieve SharedPreferences for storing favorite cryptocurrency symbols.
        // Retrieve the existing comma-separated string of favorite symbols from SharedPreferences.
        // Obtain an editor to modify SharedPreferences.
        // Initialize a mutable set to hold individual favorite symbols.
        // Parse the existing string of favorite symbols: Split the string at each comma and add symbols to the set.
        // Add the currently selected cryptocurrency's symbol to the set of favorites.
        // Reset the value string for constructing the updated favorite symbols string.
        // Iterate through the set and reconstruct the comma-separated string of favorite symbols.
        // Store the updated favorite symbols string in SharedPreferences.
        // Display a toast indicating that the cryptocurrency has been selected as a favorite.
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
            Toast.makeText(context,"Selected as favourite's",Toast.LENGTH_SHORT).show()
        }
    }
    private fun setUpBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val transaction: FragmentTransaction? =
                    parentFragmentManager.beginTransaction()
                if (transaction != null) {
                    transaction.replace(R.id.NavHostFragment, TopCryptoFragment(),null)
                    transaction.commit()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }



}

