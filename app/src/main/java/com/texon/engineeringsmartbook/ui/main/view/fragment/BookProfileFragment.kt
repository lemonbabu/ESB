package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.api.BooksApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.booksModel.BookDashboardData
import com.texon.engineeringsmartbook.data.model.booksModel.TopicsContent
import com.texon.engineeringsmartbook.databinding.FragmentBookProfileBinding
import com.texon.engineeringsmartbook.ui.main.adapter.BookDashboardTopicContentAdapter
import com.texon.engineeringsmartbook.ui.main.view.activities.VideoPlayer
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import kotlinx.coroutines.*
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class BookProfileFragment : Fragment(R.layout.fragment_book_profile), BookDashboardTopicContentAdapter.OnSubTopicClickListener{

    private lateinit var binding: FragmentBookProfileBinding
    private lateinit var token: String
    private val bookDetails: BooksApiInterfaces.BooksDashboardInterface by lazy { RetrofitClient.getBookDashboard() }
    private val videoLink: BooksApiInterfaces.TopicAccessInterface by lazy { RetrofitClient.getTopicAccess() }
    private lateinit var adapter : BookDashboardTopicContentAdapter
    private lateinit var rows : MutableList<TopicsContent>
    private lateinit var recentSet: MutableSet<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookProfileBinding.bind(view)
        val id = arguments?.getInt("id")

        loadToken()

        binding.loader.layoutLoader.visibility = View.VISIBLE
        rows = mutableListOf()

        id?.let { loadBook(it) }

    }

    private fun loadBook(id: Int){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = bookDetails.booksDashboardData(id).awaitResponse()
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        response.body()?.data?.let {
                            binding.loader.layoutLoader.visibility = View.GONE
                            binding.bookInfo.txtBookTitle.text = it.name
                            Picasso.get().load(it.avatar).fit().into(binding.bookInfo.imgBookCover)
                            populateData(it.chapterDetails)
                        }
                        //Log.d("Book Profile", response.body()?.data.toString())
                    }
                }

            }catch (e: Exception) {
                Log.d("Error", e.message.toString())
                withContext(Dispatchers.Main) {
                    binding.loader.layoutLoader.visibility = View.GONE
                    Toast.makeText( context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //Log.d("Book Profile", "Loading book contain")
    }


    private fun populateData(list: MutableList<BookDashboardData.Data.ChapterDetail>){

        for (i in list){
//            val topicList : MutableList<BookDashboardData.Data.ChapterDetail.Topic> = mutableListOf()
//            for (j in i.topic){
//                topicList.add(BookDashboardData.Data.ChapterDetail.Topic(id = j.id, name = j.name, avatar = j.avatar, description = j.description))
//            }
            rows.add(TopicsContent(TopicsContent.TOPICS, BookDashboardData.Data.ChapterDetail(i.id, i.name, i.topic)))
        }

        //Toast.makeText( context, rows.toString(), Toast.LENGTH_SHORT).show()
        binding.rvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = BookDashboardTopicContentAdapter(rows, this)
        binding.rvContent.adapter = adapter
    }

    override fun onSubTopicClickListener(id: Int, avatar: String, name: String) {
        binding.loader.layoutLoader.visibility = View.VISIBLE
        getLink(id)

        val subTopicSet = id.toString() + "œ" + avatar + "œ" + name
        recentSet = mutableSetOf()


        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("RecentView", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            recentSet = sharedPreferences.getStringSet("data", null) as MutableSet<String>
        }

        recentSet.add(subTopicSet)

        val editor = sharedPreferences?.edit()
        editor?.apply{
            putBoolean("session", true)
            putStringSet("data", recentSet)
        }?.apply()

        Log.d("Recent= ", recentSet.toString())
    }


    private fun getLink(id: Int){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = videoLink.topicData(id, "Bearer $token").awaitResponse()
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        response.body()?.data?.let {
                            //Toast.makeText(requireContext(),it.youtube_id, Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), VideoPlayer::class.java)
                            intent.putExtra("link", it.youtube_id)
                            startActivity(intent)
                        }
                        //Log.d("Book Profile", response.body()?.data.toString())
                    }
                }

            }catch (e: Exception) {
                Log.d("Error", e.message.toString())
                withContext(Dispatchers.Main) {
                    binding.loader.layoutLoader.visibility = View.GONE
                    Toast.makeText( context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.loader.layoutLoader.visibility = View.GONE
        //Log.d("Book Profile", "Loading book contain")
    }


    private fun loadToken(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            token = sharedPreferences.getString("token", "").toString()
        }
        else{
            val intent = Intent(this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }


}