package com.example.tweetssearchapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.tweetssearchapp.ui.screens.MainScreen
import com.example.tweetssearchapp.ui.screens.MainViewModel
import com.example.tweetssearchapp.ui.theme.TweetsSearchAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val networkRequest by lazy {
        NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        private val networks = mutableListOf<Network>()

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            networks.add(network)
            lifecycleScope.launch {
                viewModel.setNetworkStatus(true)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            networks.removeIf { it.toString() == network.toString() }
            lifecycleScope.launch {
                viewModel.setNetworkStatus(false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        setContent {
            TweetsSearchAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TweetsSearchApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun TweetsSearchApp(viewModel: MainViewModel) {
    val tweetsList = viewModel.tweetsList.collectAsState().value
    MainScreen(tweets = tweetsList, onSearchBtnClick = { viewModel.setSearchRule(it) })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TweetsSearchAppTheme {

    }
}
