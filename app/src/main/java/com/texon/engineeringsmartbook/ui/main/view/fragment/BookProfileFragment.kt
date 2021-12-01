package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.booksModel.BookDetailsDataModel
import com.texon.engineeringsmartbook.databinding.FragmentBookProfileBinding
import com.texon.engineeringsmartbook.ui.main.adapter.BookTopicContentAdapter
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
class BookProfileFragment : Fragment(R.layout.fragment_book_profile), BookTopicContentAdapter.OnBookClickListener {

    private lateinit var binding: FragmentBookProfileBinding
    private val bookDetails: ApiInterfaces.BooksDetailsInterface by lazy { RetrofitClient.getBookDetails() }
    private lateinit var adapter : BookTopicContentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookProfileBinding.bind(view)
        val id = arguments?.getInt("id")

        binding.loader.layoutLoader.visibility = View.VISIBLE
        binding.rvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = BookTopicContentAdapter(this)
        binding.rvContent.adapter = adapter
        binding.loader.layoutLoader.visibility = View.VISIBLE
        id?.let { loadBook(it) }

    }

    private fun loadBook(id: Int){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = bookDetails.booksDetailsData(id).awaitResponse()
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        response.body()?.data?.let {
                            binding.loader.layoutLoader.visibility = View.GONE
                            binding.bookInfo.txtBookTitle.text = it.name
                            Picasso.get().load(it.avatar).fit().into(binding.bookInfo.imgBookCover)
                            adapter.submitList(it.chapterDetails)
                        }
                        Log.d("Book Profile", response.toString())
                    }
                }

            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.loader.layoutLoader.visibility = View.GONE
                    Toast.makeText( context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.d("Book Profile", "Loading book contain")
    }

    override fun onBookClickListener(result: BookDetailsDataModel.Data.ChapterDetail) {

    }
}