package com.example.composecontacts.list.data

import com.example.composecontacts.core.network.ReqResService
import com.example.composecontacts.core.network.ReqResSingleResponse
import com.example.composecontacts.core.network.ReqResUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(
  private val service: ReqResService
) {
  suspend fun loadPage(page: Int) = withContext(Dispatchers.IO) {
    service.getUsers(page)
  }

  suspend fun getById(id: Int): ReqResSingleResponse = withContext(Dispatchers.IO) {
    service.getUser(id)
  }
}
