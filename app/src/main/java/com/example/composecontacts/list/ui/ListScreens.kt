package com.example.composecontacts.list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.composecontacts.list.ui.vm.ContactsListUiState
import com.example.composecontacts.list.ui.vm.ContactsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsListScreen(
  viewModel: ContactsListViewModel = hiltViewModel(),
  onContactClick: (Int) -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Contacts") }
      )
    }
  ) { paddingValues ->
    when (val currentState = state) {
      is ContactsListUiState.Loading -> {
        BoxLoading(modifier = Modifier.padding(paddingValues))
      }

      is ContactsListUiState.Error -> {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(currentState.message)
        }
      }

      is ContactsListUiState.Loaded -> {
        if (currentState.contacts.isEmpty()) {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text("No contacts available")
          }
        } else {
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .padding(paddingValues)
          ) {
            items(currentState.contacts, key = { it.id }) { user ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { onContactClick(user.id) }
                  .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Image(
                  painter = rememberAsyncImagePainter(user.avatar),
                  contentDescription = null,
                  modifier = Modifier.size(56.dp),
                  contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                  Text(text = "${user.firstName} ${user.lastName}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                  Text(text = user.email, style = MaterialTheme.typography.bodySmall)
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun BoxLoading(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CircularProgressIndicator()
  }
}

@Preview
@Composable
fun BoxLoadingPreview() {
  BoxLoading()
}
