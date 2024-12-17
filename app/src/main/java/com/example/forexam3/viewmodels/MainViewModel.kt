package com.example.forexam3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forexam3.models.Products
import com.example.forexam3.models.categories
import com.example.forexam3.models.countryProd
import com.example.forexam3.models.user
import com.example.forexam3.service.Constants
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    public sealed class Result{
        data class Success(val user: Any) : Result()
        data class Error(val message: String?) : Result()
    }
    val _auth = MutableStateFlow<Result?>(null)
    var Auth: StateFlow<Result?> = _auth
    val _reg = MutableStateFlow<Result?>(null)
    var Reg: StateFlow<Result?> = _reg

    fun SignIn(my_email: String, parol: String){
        viewModelScope.launch {
            try {
                val user = Constants.supabase.auth.signInWith(Email){
                    email = my_email
                    password = parol
                }
                _auth.value = Result.Success(user)
            }
            catch (e: Exception){
                println(e.message)
                println(e.stackTrace)
                _auth.value = Result.Error(e.message.toString())
            }
        }
    }

    fun SignUp(my_email: String, parol: String, name: String, phone:String, birthday:String){
        viewModelScope.launch {
            try {
                val userA = Constants.supabase.auth.signUpWith(Email){
                    email = my_email
                    password = parol
                }
                val userU = Constants.supabase.from("user").insert(
                    mapOf(
                        "name" to name, "phone" to phone, "birthday" to birthday,
                        "id" to Constants.supabase.auth.currentUserOrNull()!!.id
                    )
                )
                _reg.value = Result.Success(user = userU)
            }
            catch (e: Exception){
                println(e.message)
                println(e.stackTrace)
                _reg.value = Result.Error(e.message.toString())
            }
        }
    }



    val _prodList = MutableStateFlow<List<Products>>(emptyList())
    var ProdList: StateFlow<List<Products>> = _prodList

    val _catList = MutableStateFlow<List<categories>>(emptyList())
    var CatList: StateFlow<List<categories>> = _catList

    val _userList = MutableStateFlow<List<user>>(emptyList())
    var UserList: StateFlow<List<user>> = _userList

    val _isLoaded = MutableStateFlow<Boolean>(false)
    var IsLoaded: StateFlow<Boolean> = _isLoaded

    fun loadProducts(){
        viewModelScope.launch {
            try{
                println("!!!!зашли в loadProducts")
                val prodFromDb = Constants.supabase.from("products").select().decodeList<Products>()
                println("!!!!!!!!!!!get product list")
                val cp = Constants.supabase.from("countryProd").select().decodeList<countryProd>()
                val c = Constants.supabase.from("categories").select().decodeList<categories>()
                _prodList.value = prodFromDb.map { formap ->
                    val country = cp.firstOrNull{ it.id == formap.country_id }
                    formap.country = country
                    println("get country ${formap.title} "+country!!.title)
                    val cat = c.firstOrNull{it.id == formap.categories_id}
                    formap.category = cat
                    println("get category ${formap.title} "+cat!!.title)
                    formap
                }
                _catList.value = c
                _userList.value = Constants.supabase.from("user").select().decodeList()
                _isLoaded.value = true
            }
            catch (e: Exception){
                println(e.message)
                println(e.stackTrace)
            }
        }
    }
    init {
        loadProducts()
    }

    fun addCat(category: String){
        viewModelScope.launch {
            Constants.supabase.from("categories").insert( mapOf("title" to category) )
            _catList.value = Constants.supabase.from("categories").select().decodeList()
        }
    }
/*    fun addProd(prod: Products){
        viewModelScope.launch {
            Constants.supabase.from("products").insert(
                mapOf(
                    "title" to prod.title, "price" to prod.price,
*//*                    "title" to prod.title, "title" to prod.title,
                    "title" to prod.title, "title" to prod.title,
                    "title" to prod.title, "title" to prod.title,*//*
                )
            )
            _prodList.value = Constants.supabase.from("products").select().decodeList()
        }
    }*/

    fun delCat(id: String){
        viewModelScope.launch {
            Constants.supabase.from("categories").delete{ select(); filter { eq("id", id) } }
            _catList.value = Constants.supabase.from("categories").select().decodeList()
        }
    }

    fun updateCat(id: String, title: String){
        viewModelScope.launch {
            Constants.supabase.from("categories")
                .update(mapOf("title" to title), { filter { eq("id", id) } })
            _catList.value = Constants.supabase.from("categories").select().decodeList()
        }
    }
}