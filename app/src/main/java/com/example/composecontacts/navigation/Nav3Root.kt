package com.example.composecontacts.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.composecontacts.detail.ui.ContactDetailScreen
import com.example.composecontacts.list.ui.ContactsListScreen

@Composable
fun Nav3Root() {
  val backStack = rememberSaveable(saver = RouteKeySaver()) { mutableStateListOf(RouteKey.List) }

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<RouteKey.List> {
        ContactsListScreen { id ->
          backStack.add(RouteKey.Detail(id))
        }
      }
      entry<RouteKey.Detail> { key ->
        ContactDetailScreen(
          userId = key.userId,
          onBackPressed = { backStack.removeLastOrNull() }
        )
      }
    }
  )
}

// Saver for RouteKey for rememberSaveable
@Composable
private fun RouteKeySaver() =
  androidx.compose.runtime.saveable.Saver<MutableList<RouteKey>, List<RouteKey>>(
    save = { it.toList() },
    restore = { it.toMutableList() }
  )
