package com.example.composecontacts.list.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecontacts.core.network.ReqResUser
import com.example.composecontacts.list.data.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ContactsListUiState {
  data class Loaded(
    val contacts: List<ReqResUser> = emptyList(),
  ) : ContactsListUiState()

  data object Loading : ContactsListUiState()

  data class Error(val message: String) : ContactsListUiState()
}

@HiltViewModel
class ContactsListViewModel @Inject constructor(
  val repository: ContactsRepository
) : ViewModel() {
  private val _state = MutableStateFlow<ContactsListUiState>(ContactsListUiState.Loading)
  val state = _state.asStateFlow()

  init {
    loadContacts()
  }

  private fun loadContacts() {
    _state.value = ContactsListUiState.Loading
    viewModelScope.launch {
      try {
        val resp = repository.loadPage(0)
        _state.value = ContactsListUiState.Loaded(contacts = resp.data)
      } catch (e: Exception) {
        android.util.Log.e("Contacts", "Failed to load contacts", e)
        _state.value = ContactsListUiState.Error(e.message ?: "Unknown error")
      }
    }
  }
}
