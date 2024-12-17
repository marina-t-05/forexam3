package com.example.forexam3.views

import android.widget.Toast
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forexam3.MainViewModel

@Composable
fun Avtorization(navHost: NavHostController, viewModel: MainViewModel){
    var email by remember { mutableStateOf("") }
    var parol by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    val authResult by viewModel.Auth.collectAsState()
    val rr by viewModel.Reg.collectAsState()
    val ctx = LocalContext.current

    Column(modifier = Modifier.padding(top = 30.dp)) {
        TextField(
            value = email,
            onValueChange = { nt -> email = nt }
        )
        TextField(
            value = parol,
            onValueChange = { np -> parol = np }
        )
        TextField(
            value = name,
            onValueChange = { np -> name = np }
        )
        TextField(
            value = phone,
            onValueChange = { np -> phone = np }
        )
        TextField(
            value = birthday,
            onValueChange = { np -> birthday = np }
        )
        Button(
            onClick = { viewModel.SignIn(email, parol) }
        ) { Text("Войти") }
        Button(
            onClick = { viewModel.SignUp(email, parol, name, phone, birthday) }
        ) { Text("Зарегистрироваться") }
    }

    // Обработка результата авторизации
    when {
        authResult is MainViewModel.Result.Success -> {
            // Если авторизация успешна, навигация на другой экран
            println("сейчас перейдем на другой экран")
            navHost.navigate("StartView")
        }
        authResult is MainViewModel.Result.Error -> {
            // Если произошла ошибка, показываем сообщение об ошибке
            Toast.makeText(ctx, "Error: ${(authResult as MainViewModel.Result.Error).message}", Toast.LENGTH_SHORT).show()
        }
        authResult == null -> {
            // Ожидание результата, ничего не делаем
        }
    }

    // Обработка результата авторизации
    when {
        rr is MainViewModel.Result.Success -> {
            // Если авторизация успешна, навигация на другой экран
            println("сейчас перейдем на другой экран")
            navHost.navigate("StartView")
        }
        rr is MainViewModel.Result.Error -> {
            // Если произошла ошибка, показываем сообщение об ошибке
            Toast.makeText(ctx, "Error: ${(rr as MainViewModel.Result.Error).message}", Toast.LENGTH_SHORT).show()
        }
        rr == null -> {
            // Ожидание результата, ничего не делаем
        }
    }

}