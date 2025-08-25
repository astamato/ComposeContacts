package com.example.composecontacts.core.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface ReqResService {
  suspend fun getUsers(page: Int): ReqResListResponse

  suspend fun getUser(id: Int): ReqResSingleResponse
}

class KtorReqResService @Inject constructor(
  private val httpClient: HttpClient,
) : ReqResService {
  private val baseUrl = "https://reqres.in/api"

  override suspend fun getUsers(page: Int): ReqResListResponse {
    val response = httpClient.get("$baseUrl/users") {
      parameter("page", page)
      parameter("per_page", 6)
      header(HttpHeaders.Accept, ContentType.Application.Json)
      header("x-api-key", "reqres-free-v1")
    }
    val text = response.bodyAsText()
    return try {
      Json { ignoreUnknownKeys = true }.decodeFromString(text)
    } catch (e: Exception) {
      Log.e("ReqResService", "Decode error, body=\n$text", e)
      throw e
    }
  }

  override suspend fun getUser(id: Int): ReqResSingleResponse =
    httpClient
      .get("$baseUrl/users/$id") {
        header("x-api-key", "reqres-free-v1")
      }
      .body<ReqResSingleResponse>()
}
