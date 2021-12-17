package com.texon.engineeringsmartbook.ui.main.view.activities


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.booksModel.BookDetailsDataModel
import com.texon.engineeringsmartbook.databinding.ActivityBookDetailsBinding
import com.texon.engineeringsmartbook.ui.main.adapter.BookTopicContentAdapter
import com.texon.engineeringsmartbook.ui.main.view.MainActivity
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
class BookDetails : AppCompatActivity(), BookTopicContentAdapter.OnBookClickListener {

    private lateinit var binding: ActivityBookDetailsBinding
    private val bookDetails: ApiInterfaces.BooksDetailsInterface by lazy { RetrofitClient.getBookDetails() }
    private lateinit var adapter : BookTopicContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("id", 5)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)



        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = BookTopicContentAdapter(this)
        binding.rvContent.adapter = adapter
        binding.loader.layoutLoader.visibility = View.VISIBLE
        Log.d("Book Details", id.toString())
        loadBook(id)

        binding.layoutHeader.btnBackMenu.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("nav", "home")
            startActivity(intent)
            finish()
        }

        binding.btnGetAccess.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("nav", "bookAccess")
            startActivity(intent)
            finish()
        }

        binding.btnOrderNow.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("nav", "orderNow")
            startActivity(intent)
            finish()
        }

//        val sharedPreferences: SharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
//        val session = sharedPreferences.getBoolean("session", false)
//        if(!session){
//            val intent = Intent(applicationContext, Login::class.java)
//            startActivity(intent)
//            finish()
//        }else{
//            token = sharedPreferences.getString("session", null).toString()
//        }
//
//        Toast.makeText(applicationContext, "$token ",Toast.LENGTH_SHORT).show()

    }

    private fun loadBook(id: Int){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = bookDetails.booksDetailsData(id).awaitResponse()
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        response.body()?.data?.let {
                            binding.loader.layoutLoader.visibility = View.GONE
                            binding.txtBookTitle.text = it.name
                            binding.txtBookPrice.text = it.price.toString()
                            binding.txtBookDiscount.text = it.discount_value.toString()
                            binding.txtBookSold.text = it.total_sale.toString()
                            binding.txtBookDescription.text = Html.fromHtml(it.description)
                            Picasso.get().load(it.avatar).fit().into(binding.imgBooksCover)
                            adapter.submitList(it.chapterDetails)

                            val sharedPreferences = getSharedPreferences("BookAccess", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.apply{
                                putBoolean("session", true)
                                putInt("id", id)
                                putString("avatar", it.avatar)
                                putString("title", it.name)
                            }.apply()
                        }
                    }
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText( applicationContext,"Internet Connection is not stable!!",Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.d("Slider Image", "Loading slider images")
    }

    override fun onBookClickListener(result: BookDetailsDataModel.Data.ChapterDetail) {
//        val intent = Intent(applicationContext, BookDetails::class.java)
//        intent.putExtra("id", result.id)
//        startActivity(intent)
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("nav", "home")
        startActivity(intent)
        finish()
    }

}