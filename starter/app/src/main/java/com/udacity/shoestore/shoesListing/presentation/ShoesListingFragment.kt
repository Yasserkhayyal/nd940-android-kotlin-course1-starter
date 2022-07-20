package com.udacity.shoestore.shoesListing.presentation

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoesListingBinding
import com.udacity.shoestore.databinding.ShoeItemLayoutBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.utils.SHARED_PREFS_NAME

class ShoesListingFragment : Fragment() {

    private lateinit var binding: FragmentShoesListingBinding
    private lateinit var viewModelFactory: ShoeListingViewModelProviderFactory
    private lateinit var viewModel: ShoeListingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        viewModelFactory = ShoeListingViewModelProviderFactory(
            requireActivity().getSharedPreferences(
                SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )
        )
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[ShoeListingViewModel::class.java]
        binding = DataBindingUtil.inflate<FragmentShoesListingBinding>(
            inflater,
            R.layout.fragment_shoes_listing,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<FloatingActionButton>(R.id.add_shoes_item)
            .setOnClickListener {
                requireView().findNavController()
                    .navigate(R.id.action_shoesListingFragment_to_shoesDetailsFragment)
            }
        if (!viewModel.isUserLoggedIn()) {
            findNavController().navigate(R.id.action_shoesListingFragment_to_loginFragment)
        }
        viewModel.shoesList.observe(viewLifecycleOwner) { shoesList ->
            binding.shoesList.removeAllViews()
            for (shoe in shoesList) {
                addView(shoe)
            }
        }
    }

    private fun addView(shoe: Shoe) {
        DataBindingUtil.inflate<ShoeItemLayoutBinding>(
            layoutInflater,
            R.layout.shoe_item_layout,
            binding.shoesList,
            false
        ).apply {
            this.shoe = shoe
            binding.shoesList.addView(root)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shoes_listing_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*as we don't want to popup back from the LoginFragment to the ShoesListingFragment, we can't use
        NavigationUI.onNavDestinationSelected(
            item,
            findNavController(),
        ) since it internally passes an empty NavOptions in navController.navigate(item.getItemId(), null, options) which doesn't fit our need here*/
        return if (item.itemId == R.id.loginFragment) {
            viewModel.setUserLoggedOut()
            findNavController().navigate(R.id.action_shoesListingFragment_to_loginFragment)
            true
        } else {
            NavigationUI.onNavDestinationSelected(
                item,
                findNavController(),
            ) || super.onOptionsItemSelected(item)
        }
    }


}