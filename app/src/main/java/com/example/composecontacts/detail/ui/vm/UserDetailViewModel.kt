package com.example.composecontacts.detail.ui.vm

import androidx.lifecycle.ViewModel
import com.example.composecontacts.core.network.ReqResSingleResponse
import com.example.composecontacts.list.data.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
  private val repository: ContactsRepository
) : ViewModel() {
  suspend fun getById(id: Int): ReqResSingleResponse = repository.getById(id)
}
