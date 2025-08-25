package com.example.composecontacts.detail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.composecontacts.core.network.ReqResSingleResponse
import com.example.composecontacts.core.network.ReqResUser
import com.example.composecontacts.detail.ui.vm.UserDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
  userId: Int,
  viewModel: UserDetailViewModel = hiltViewModel(),
  onBackPressed: () -> Unit = {}
) {
  var response by remember { mutableStateOf<ReqResSingleResponse?>(null) }
  var loading by remember { mutableStateOf(true) }
  var error by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(userId) {
    loading = true
    error = null
    runCatching { viewModel.getById(userId) }
      .onSuccess { response = it }
      .onFailure {
        response = null
        error = it.message ?: "Failed to load user"
      }
    loading = false
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Contact Details") },
        navigationIcon = {
          IconButton(onClick = onBackPressed) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
          }
        }
      )
    }
  ) { paddingValues ->
    when {
      loading -> {
        Column(
          modifier = Modifier.fillMaxSize().padding(paddingValues),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          CircularProgressIndicator()
        }
      }
      error != null -> {
        Column(
          modifier = Modifier.fillMaxSize().padding(paddingValues),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("Error: $error")
        }
      }
      response != null -> {
        val u = response!!.data
        val support = response!!.support
        Column(
          modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp).verticalScroll(rememberScrollState()),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Image(
            painter = rememberAsyncImagePainter(u.avatar),
            contentDescription = null,
            modifier = Modifier.size(128.dp).align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
          )
          Spacer(modifier = Modifier.size(8.dp))
          Text(text = "${u.firstName} ${u.lastName}", style = MaterialTheme.typography.headlineSmall)
          Text(text = u.email, style = MaterialTheme.typography.bodyLarge)

          Spacer(modifier = Modifier.size(16.dp))
          Text(text = "Support", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Text(text = support.text, style = MaterialTheme.typography.bodyMedium)
          Text(text = support.url, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
      }
    }
  }
}
