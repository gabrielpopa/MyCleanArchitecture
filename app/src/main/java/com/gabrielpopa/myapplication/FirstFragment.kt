package com.gabrielpopa.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginResponse
import com.gabrielpopa.myapplication.databinding.FragmentFirstBinding
import com.gabrielpopa.myapplication.domain.login.entity.LoginEntity
import com.gabrielpopa.myapplication.presentation.login.LoginActivityState
import com.gabrielpopa.myapplication.presentation.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val viewModel: LoginViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.mState.collect {
                    handleStateChange(it)
                }
            }
        }
    }

    private fun handleStateChange(state: LoginActivityState){
        when(state){
            is LoginActivityState.Init -> Unit
            is LoginActivityState.ErrorLogin -> handleErrorLogin(state.rawResponse)
            is LoginActivityState.SuccessLogin -> handleSuccessLogin(state.loginEntity)
            is LoginActivityState.ShowToast -> Log.e("state", state.message)
            is LoginActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleLoading(isLoading: Boolean){
        if (isLoading)
            binding.textviewFirst.text =  "loading..."
    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccessLogin(loginEntity: LoginEntity){
        binding.textviewFirst.text =  "Welcome ${loginEntity.name}"
    }

    private fun handleErrorLogin(response: WrappedResponse<LoginResponse>){
        binding.textviewFirst.text =  response.message
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        // Observe states
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}