package com.example.composecontacts.list.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composecontacts.detail.ui.ContactDetailScreen

object Routes {
  const val List = "list"
  const val Detail = "detail/{userId}"
}

@Composable
fun ContactsNavHost(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController()
) {

  NavHost(
    modifier = modifier, navController = navController,
    startDestination = Routes.List
  ) {
    composable(Routes.List) {
      ContactsListScreen { id ->
        navController.navigate("detail/$id")
      }
    }
    composable(
      route = Routes.Detail,
      arguments = listOf(navArgument("userId") { type = NavType.IntType })
    ) { backStackEntry ->
      val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
      ContactDetailScreen(
        userId = userId,
        onBackPressed = { navController.popBackStack() }
      )
    }
  }
}
