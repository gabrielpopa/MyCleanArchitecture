package com.gabrielpopa.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.login.remote.api.LoginApi
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginRequest
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginResponse
import com.gabrielpopa.myapplication.data.login.repository.LoginRepositoryImpl
import com.gabrielpopa.myapplication.databinding.ActivityMainBinding
import com.gabrielpopa.myapplication.domain.login.LoginRepository
import com.gabrielpopa.myapplication.domain.login.entity.LoginEntity
import com.gabrielpopa.myapplication.domain.login.usecase.LoginUseCase
import com.gabrielpopa.myapplication.presentation.login.LoginActivityState
import com.gabrielpopa.myapplication.presentation.login.LoginViewModel
import com.gabrielpopa.myapplication.presentation.login.LoginViewModelFactory
//import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel : LoginViewModel by viewModels {
        val okHttp = OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            //addInterceptor(requestInterceptor)
        }.build()
        val retrofit = Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl("https://golang-heroku.herokuapp.com/api/")
        }.build()
        val loginApi = retrofit.create(LoginApi::class.java)
        val loginRepository = LoginRepositoryImpl(loginApi)
        LoginViewModelFactory(LoginUseCase(loginRepository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            val loginRequest = LoginRequest("assdfd@asdfsd.com", "sjdkfhkshjdf")
            viewModel.login(loginRequest)
        }

        // Observe states
        observe()
    }

    private fun observe(){
        viewModel.mState
            .flowWithLifecycle(lifecycle,  Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: LoginActivityState){
        when(state){
            is LoginActivityState.Init -> Unit // do nothing
            is LoginActivityState.ErrorLogin -> handleErrorLogin(state.rawResponse)
            is LoginActivityState.SuccessLogin -> handleSuccessLogin(state.loginEntity)
            is LoginActivityState.ShowToast -> Log.e("state", state.message)
            is LoginActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.fab.alpha = if (isLoading) 0.5f else 1.0f
        if (isLoading)
            binding.textviewActivity.text = "loading..."
    }

    private fun handleSuccessLogin(loginEntity: LoginEntity) {
        binding.textviewActivity.text = "Welcome ${loginEntity.name}"
    }

    private fun handleErrorLogin(response: WrappedResponse<LoginResponse>) {
        binding.textviewActivity.text = response.message
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}