package com.keshogroup.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keshogroup.template.ui.navigation.mainNavigationGraph
import com.keshogroup.template.ui.theme.TemplateTheme
import com.keshogroup.template.ui.viewmodels.ActivityDiagnosticsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityViewModel: ActivityDiagnosticsViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.activityStateFlow.collect { currentActivityState ->
                    //do something with state
                    currentActivityState
                }
            }
        }
        enableEdgeToEdge()
        setContent {
            TemplateTheme {
                mainNavigationGraph(activityDiagnosticsViewModel = activityViewModel)
            }
        }
    }

}