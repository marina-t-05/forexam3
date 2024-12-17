package com.example.forexam3.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.forexam3.MainViewModel
import com.example.forexam3.ui.theme.PurpleGrey40

@Composable
fun ProductCard(viewModel: MainViewModel, navHost: NavHostController, id: String){
    Column {
        Button(onClick = { navHost.navigateUp() }, modifier = Modifier.padding(30.dp)) { Text("Назад") }
        val idl by viewModel.IsLoaded.collectAsState()
        if (idl == false)
        {
            CircularProgressIndicator()
        }
        else{
            ProductCardData(viewModel, navHost, id)
        }
    }
}

@Composable
fun ProductCardData(viewModel: MainViewModel, navHost: NavHostController, id: String){
    val prodList by viewModel.ProdList.collectAsState()
    val product = prodList.first{it.id == id}
    Column(modifier = Modifier
        .border(5.dp, PurpleGrey40)
        .padding(5.dp)
        .clickable { navHost.navigate("ProductCard/${product.id}") }){
        Text(product.title)
        Text(product.article.toString())
        Text(product.grams.toString())
        Text(product.sing_18.toString())
        Text(product.description)
        Text(product.price.toString())
        //Text(item.image)
        Box(modifier = Modifier
            .width(100.dp)
            .height(100.dp))
        {
            AsyncImage(model = product.image, contentDescription = "", contentScale = ContentScale.Crop)
        }
        Text(product.rating.toString())
        Text(product.country!!.title)
        Text(product.category!!.title)
        Text(product.countRev.toString())
    }
}