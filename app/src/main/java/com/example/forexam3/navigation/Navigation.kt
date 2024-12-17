package com.example.forexam3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.forexam3.MainViewModel
import com.example.forexam3.views.AddCategory
import com.example.forexam3.views.AddProduct
import com.example.forexam3.views.Avtorization
import com.example.forexam3.views.ProductCard
import com.example.forexam3.views.StartView
import com.example.forexam3.views.UpdateCategory

@Composable
fun Navigation(viewModel: MainViewModel){
    val navController = rememberNavController()
    NavHost(navController, "Avtorization") {
        composable("StartView"){
            StartView(navController, viewModel)
        }
        composable("Avtorization"){
            Avtorization(navController, viewModel)
        }
        composable(
            route = "ProductCard/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.StringType
                }
            )){it ->
            ProductCard(viewModel, navController, it.arguments!!.getString("id")!!)
        }
        composable(
            route = "UpdateCategory/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.StringType
                }
            )){it ->
            UpdateCategory(navController, viewModel, it.arguments!!.getString("id")!!)
        }
        composable("AddCategory"){
            AddCategory(navController, viewModel)
        }
/*        composable(
            route = "UpdateCategory/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.StringType
                }
            )){it ->
            UpdateCategory(navController, viewModel, it.arguments!!.getString("id")!!)
        }*/
/*        composable("AddProduct"){
            AddProduct(navController, viewModel)
        }*/
    }
}
