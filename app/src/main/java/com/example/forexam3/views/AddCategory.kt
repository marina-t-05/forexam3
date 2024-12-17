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

@Composable
fun AddCategory(navHost: NavHostController, viewModel: MainViewModel){
    var title by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(top = 30.dp)) {
        TextField(
            value = title,
            onValueChange = { nt -> title = nt }
        )
        Button(
            onClick = { navHost.navigate("StartView"); viewModel.addCat(title)  }
        ) { Text("Добавить") }
    }
}

@Composable
fun UpdateCategory(navHost: NavHostController, viewModel: MainViewModel, id: String){
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
        ) { Text("Изменить") }
    }
}