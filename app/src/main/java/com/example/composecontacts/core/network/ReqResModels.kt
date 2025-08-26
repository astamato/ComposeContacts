package com.example.composecontacts.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReqResUser(
  val id: Int,
  val email: String,
  @SerialName("first_name") val firstName: String,
  @SerialName("last_name") val lastName: String,
  val avatar: String,
)

@Serializable
data class ReqResListResponse(
  val page: Int,
  @SerialName("per_page") val perPage: Int,
  val total: Int,
  @SerialName("total_pages") val totalPages: Int,
  val data: List<ReqResUser>
)

@Serializable
data class ReqResSupport(
  val url: String,
  val text: String
)

@Serializable
data class ReqResSingleResponse(
  val data: ReqResUser,
  val support: ReqResSupport
)
