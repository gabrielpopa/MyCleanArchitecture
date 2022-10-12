package com.gabrielpopa.myapplication.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.gabrielpopa.myapplication.R
import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginRequest
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondRequest
import com.gabrielpopa.myapplication.databinding.ActivityMainBinding
import com.gabrielpopa.myapplication.domain.common.Entity
import com.gabrielpopa.myapplication.presentation.common.FlowState
import com.gabrielpopa.myapplication.presentation.login.LoginViewModel
import com.gabrielpopa.myapplication.presentation.second.SecondViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel : LoginViewModel by viewModels()
    private val secondViewModel : SecondViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            val loginRequest = LoginRequest("johndoe@johndoe.com", "johndoe")
            viewModel.login(loginRequest)
        }

        binding.second.setOnClickListener {
            val req = SecondRequest("John Doe", "johndoe@johndoe.com", "johndoe")
            secondViewModel.doNetworkCall(req)
        }

        // Observe states
        observe()
    }

    private fun observe(){
        viewModel.mState
            .flowWithLifecycle(lifecycle,  Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)

        secondViewModel.mState
            .flowWithLifecycle(lifecycle,  Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: FlowState){
        when(state){
            is FlowState.Init -> Unit // do nothing
            is FlowState.ShowToast -> Log.e("state", state.message)
            is FlowState.IsLoading -> handleLoading(state.isLoading)
            is FlowState.Loaded.Success -> handleSuccess(state.value)
            is FlowState.Loaded.Error-> handleError(state.value)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleLoading(isLoading: Boolean) {
        binding.fab.alpha = if (isLoading) 0.5f else 1.0f
        if (isLoading)
            binding.textviewActivity.text = "loading..."
    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccess(entity: Entity) {
        binding.textviewActivity.text = "Welcome ${entity.name}"
    }

    private fun handleError(response: WrappedResponse<*>) {
        binding.textviewActivity.text = response.message
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}