package iqro.mobile.retrofitexample.user

data class User(
    val avatar: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String
){
    fun getFullName() = "$first_name $last_name"
}