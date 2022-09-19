package iqro.mobile.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import iqro.mobile.retrofitexample.databinding.ActivityMainBinding
import iqro.mobile.retrofitexample.todo.TodoAdapter
import iqro.mobile.retrofitexample.user.User
import iqro.mobile.retrofitexample.user.UserAdapter
import iqro.mobile.retrofitexample.user.UserCreate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var userAdapter: UserAdapter
    val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        todoAdapter = TodoAdapter()
        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://reqres.in")
            .build()
        val api = retrofit.create(ApiInterface::class.java)

        userAdapter = UserAdapter()
        val manager = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.apply {
            layoutManager  = manager
            adapter = userAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity,manager.orientation))
        }


        val flow : Flow<PagingData<User>> = Pager(
            config = PagingConfig(pageSize = 3, enablePlaceholders = false),
            pagingSourceFactory = {UserPagingSource(api)}
        ).flow

        lifecycleScope.launch {
            flow.collect{
                userAdapter.submitData(it)
            }
        }

        lifecycleScope.launch{
            userAdapter.loadStateFlow.collect{
                binding.progressHorizontal.isVisible = it.source.append is LoadState.Loading
            }
        }

//        lifecycleScope.launch {
//          val response =  api.createUser(UserCreate("","","Mobile developer","Zohidjon"))
//            if (response.isSuccessful) {
//                Log.d(TAG, "onCreate: ${response.body()}")
//            }else{
//                Log.d(TAG, "onCreate: Sorry something with creating")
//            }
//        }
//        lifecycleScope.launch {
//          val response =  api.deleteUser(2)
//
//            if (response.isSuccessful) {
//                Log.d(TAG, "onCreate: ${response.body() } ${response.code()}")
//            }else{
//                Log.d(TAG, "onCreate: Sorry something with creating")
//            }
//        }
//
//        lifecycleScope.launch {
//         val response =   api.getTodoList().body()
//
//            response?.apply {
//                todoAdapter.submitList(this)
//            }
//            Log.d("TAG", "onCreate: ${response?.joinToString()}")
//        }
    }
}