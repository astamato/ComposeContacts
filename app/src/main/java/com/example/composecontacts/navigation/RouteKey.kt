package com.example.composecontacts.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface RouteKey : NavKey {
  @Serializable
  data object List : RouteKey

  @Serializable
  data class Detail(val userId: Int) : RouteKey
}
