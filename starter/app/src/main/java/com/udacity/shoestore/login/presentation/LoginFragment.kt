package com.udacity.shoestore.login.presentation

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
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.utils.SHARED_PREFS_NAME

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    //only valid between onCreateView() and OnDestroyView()
    private val binding: FragmentLoginBinding
        get() = _binding!!

    private lateinit var viewModelFactory: LoginViewModelProviderFactory
    private lateinit var viewModel: LoginViewModel

    private val clickListener: View.OnClickListener by lazy {
        View.OnClickListener {
            viewModel.setUserLoggedIn()
            requireView().findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelFactory = LoginViewModelProviderFactory(
            requireContext().getSharedPreferences(
                SHARED_PREFS_NAME, Context.MODE_PRIVATE
            )
        )
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[LoginViewModel::class.java]
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener(clickListener)
        binding.signupButton.setOnClickListener(clickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}