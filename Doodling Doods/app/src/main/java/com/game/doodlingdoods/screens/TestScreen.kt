package com.game.doodlingdoods.screens



//@Composable
//fun AppPreview() {
//    val navController = rememberNavController()
//    val signUpScreenViewModel = viewModel<SignUpScreenViewModel>()
//    val navGraph = remember(navController) {
//
//        navController.createGraph(startDestination = "HomeScreen") {
//            composable("HomeScreen") {
//                HomeScreen(navController = navController)
//            }
//
//            composable("AccountSetup") {
//                AccountSetup(navController = navController)
//            }
//
//            composable("LoginScreen") {
//                LoginScreen(navController = navController)
//            }
//
//            composable("SignUpScreen") {
//
//                SignUpScreen(
//                    navController = navController,
//                    signUpScreenViewModel
//                )
//            }
//        }
//
//    }
//}


fun main() {
    val map = hashMapOf("a" to 10, "b" to 5, "c" to 8, "d" to 15)

    val sortedMap = map.toList().sortedByDescending { it.second }.toMap()

    println(sortedMap)
}
