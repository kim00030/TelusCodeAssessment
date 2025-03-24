package com.example.teluscodeassesmentfromdankim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teluscodeassesmentfromdankim.presentation.home.HomeViewModel
import com.example.teluscodeassesmentfromdankim.ui.theme.TelusCodeAssesmentFromDanKimTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            TelusCodeAssesmentFromDanKimTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val temp = innerPadding
                    val homeViewModel = hiltViewModel<HomeViewModel>()
                    val state = homeViewModel.state
                }
            }
        }
    }
}
