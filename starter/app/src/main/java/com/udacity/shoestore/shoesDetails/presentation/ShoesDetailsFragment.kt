package com.udacity.shoestore.shoesDetails.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoesDetailsBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.shoesListing.presentation.ShoeListingViewModel
import com.udacity.shoestore.shoesListing.presentation.ShoeListingViewModelProviderFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ShoesDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoesDetailsFragment : Fragment() {

    private var shoes: Shoe? = null
    private var _binding: FragmentShoesDetailsBinding? = null
    private val binding: FragmentShoesDetailsBinding
        get() = _binding!!
    private lateinit var viewModelFactory: ShoeListingViewModelProviderFactory
    private lateinit var shoeListingViewModel: ShoeListingViewModel
    private val clickListener: ClickListener by lazy { ClickListener() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelFactory = ShoeListingViewModelProviderFactory(
            requireActivity().getSharedPreferences(
                "ShoeStore",
                Context.MODE_PRIVATE
            )
        )
        shoeListingViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[ShoeListingViewModel::class.java]
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate<FragmentShoesDetailsBinding?>(
            inflater,
            R.layout.fragment_shoes_details,
            container,
            false
        ).apply {
            lifecycleOwner = this@ShoesDetailsFragment
            viewModel = shoeListingViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clearInputFields() // clear input fields for new entrance as the viewModel is globally shared
        shoeListingViewModel.newShoes.observe(viewLifecycleOwner) {
            shoes = it
        }
        binding.saveBtn.setOnClickListener(clickListener)
        binding.cancelBtn.setOnClickListener(clickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            if (view == binding.saveBtn) {
                shoeListingViewModel.saveDraftShoes(shoes)
            }
            view.findNavController().popBackStack()
        }

    }

    private fun clearInputFields() {
        shoeListingViewModel.newShoeName.value = null
        shoeListingViewModel.newShoeSize.value = null
        shoeListingViewModel.newShoesCompany.value = null
        shoeListingViewModel.newShoeDescription.value = null
    }
}