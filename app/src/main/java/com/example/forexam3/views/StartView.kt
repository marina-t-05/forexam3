package com.example.forexam3.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.forexam3.MainViewModel
import com.example.forexam3.service.Constants
import com.example.forexam3.ui.theme.PurpleGrey40
import io.github.jan.supabase.auth.auth

@Composable
fun StartView(navHost: NavHostController, viewModel: MainViewModel){
    val idl by viewModel.IsLoaded.collectAsState()
    if (idl == false)
    {
        CircularProgressIndicator()
    }
    else{
        StartViewData(navHost, viewModel)
    }
}

@Composable
fun StartViewData(navHost: NavHostController, viewModel: MainViewModel){
    var searchStr by remember { mutableStateOf("") }
    val l by viewModel.ProdList.collectAsState()
    var list = l
    val cats by viewModel.CatList.collectAsState()
    if (searchStr != "")//a@gmail.com
    {
        list = l.filter { it.title.toLowerCase().contains(searchStr.toLowerCase()) ||
                it.description.toLowerCase().contains(searchStr.toLowerCase()) }
    }
    else
    {
        list = l
    }
    val userList by viewModel.UserList.collectAsState()
    val user = userList.firstOrNull{ it.id == Constants.supabase.auth.currentUserOrNull()!!.id }


    Column(modifier = Modifier.padding(top = 30.dp)) {
        Button(onClick = { navHost.navigate("AddCategory")}) { Text("Добавить категорию") }
        Text(user!!.name)
        Text(user!!.phone)
        Text(user!!.birthday)
        TextField(
            value = searchStr,
            onValueChange = { nt -> searchStr = nt }
        )
        /*Button(onClick = { navHost.navigate("AddProduct")}) { Text("Добавить продукт") }*/
        LazyColumn(modifier = Modifier.height(200.dp)) {
            items(cats){item ->
                Row {
                    Text(item.title)
                    Button(onClick = { viewModel.delCat(item.id) }) { Text("Удалить") }
                    Button(onClick = { navHost.navigate("UpdateCategory/${item.id}") }) { Text("Редактировать") }
                }
            }
        }
        LazyColumn {
            items(list){item ->
                Column(modifier = Modifier.border(5.dp, PurpleGrey40).padding(5.dp)
                    .clickable { navHost.navigate("ProductCard/${item.id}") }){
                    Text(item.title)
                    Text(item.article.toString())
                    Text(item.grams.toString())
                    Text(item.sing_18.toString())
                    Text(item.description)
                    Text(item.price.toString())
                    Box(modifier = Modifier.width(100.dp).height(100.dp))
                    {
                        AsyncImage(model = item.image, contentDescription = "", contentScale = ContentScale.Crop)
                    }
                    Text(item.rating.toString())
                    Text(item.country!!.title)
                    Text(item.category!!.title)
                    Text(item.countRev.toString())
/*                    Row {
                        Button(onClick = { viewModel.delCat(item.id!!) }) { Text("Удалить") }
                        Button(onClick = { navHost.navigate("UpdateCategory/${item.id}") }) { Text("Редактировать") }
                    }*/
                }
            }
        }
    }
}