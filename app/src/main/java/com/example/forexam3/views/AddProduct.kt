package com.example.forexam3.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forexam3.MainViewModel
import com.example.forexam3.models.Products

@Composable
fun AddProduct(navHost: NavHostController, viewModel: MainViewModel){
    var title by remember { mutableStateOf("") }
    var article by remember { mutableStateOf(0) }
    var grams by remember { mutableStateOf("") }
    var sing_18 by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(0) }
    var image by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var countRev by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(top = 30.dp)) {
        TextField(
            value = title,
            onValueChange = { nt -> title = nt }
        )
        TextField(
            value = price.toString(),
            onValueChange = { np -> price = np.toInt() }
        )
        val newProduct = Products(
            title = title,
            article = 0,
            grams = 0,
            sing_18 = false,
            description = "",
            price = price,
            image = "",
            rating = 0F,
            countRev = 0,
            categories_id = null,
            country_id = null,
            prodStatus_id = null,
            id = null
        )
        Button(
            onClick = { navHost.navigate("StartView");
                //viewModel.addProd(newProduct)
        }//ш
        ) { Text("Добавить") }
    }
}

@Composable
fun UpdateProduct(navHost: NavHostController, viewModel: MainViewModel, id: String){
    val c by viewModel.CatList.collectAsState()
    val onec = c.firstOrNull { it.id == id }
    var title by remember { mutableStateOf(onec!!.title) }

    Column(modifier = Modifier.padding(top = 30.dp)) {
        TextField(
            value = title,
            onValueChange = { nt -> title = nt }
        )
        Button(
            onClick = { navHost.navigate("StartView"); viewModel.updateCat(id, title)  }
        ) { Text("Добавить") }
    }
}