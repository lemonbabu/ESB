package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.FragmentDashboardBinding
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.BooksApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.DashboardData
import com.texon.engineeringsmartbook.data.model.booksModel.AllBooksDataModel
import com.texon.engineeringsmartbook.data.model.booksModel.BookDashboardData
import com.texon.engineeringsmartbook.data.model.booksModel.TopicsContent
import com.texon.engineeringsmartbook.ui.main.adapter.*
import com.texon.engineeringsmartbook.ui.main.view.activities.BookDetails
import com.texon.engineeringsmartbook.ui.main.view.activities.VideoPlayer
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
class DashboardFragment : Fragment(R.layout.fragment_dashboard), YourBooksAdapter.OnYourBookClickListener, AllBooksAdapter.OnBookClickListener, RecentTopicsAdapter.OnTopicsClickListener{

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var fc: FragmentCommunicator
    private var token = ""
    private val sliderImage: ApiInterfaces.SliderImageInterface by lazy { RetrofitClient.getSliderImage() }
    private val dashBoardData: ApiInterfaces.DashboardDataInterface by lazy { RetrofitClient.getDashboardData() }
    private val allBooks: ApiInterfaces.AllBooksDataInterface by lazy { RetrofitClient.getAllBooks() }
    private val videoLink: BooksApiInterfaces.TopicAccessInterface by lazy { RetrofitClient.getTopicAccess() }
    private var booksData: DashboardData.Data? = null
    private lateinit var adapterYourBook : YourBooksAdapter
    private lateinit var recentViewAdapter : RecentTopicsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        fc = activity as FragmentCommunicator
        binding.rvYourBooks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.loader.layoutLoader.visibility = View.VISIBLE

        loadProfile()
        recentView()

        try {
            setImageInSlider()
            setDashBoardData()
            allBooksData()
        }catch (e: Exception){
            Log.d("Dashboard= ", "Coroutine error.")
        }


        binding.btnYourBooks.setOnClickListener {
            booksData?.your_book.let {
                adapterYourBook = YourBooksAdapter(this)
                binding.rvYourBooks.adapter = adapterYourBook
                it?.let {
                        it1 -> adapterYourBook.submitList(it1)
                    Log.d("Dashboard Data= ", it1.toString())}
                binding.btnYourBooks.setTextColor(Color.parseColor("#4B839A"))
                binding.btnFreeVideo.setTextColor(Color.parseColor("#232323"))

            }
        }

        binding.btnFreeVideo.setOnClickListener {
            booksData?.free_video.let {
                adapterYourBook = YourBooksAdapter(this)
                binding.rvYourBooks.adapter = adapterYourBook
                it?.let { it1 -> adapterYourBook.submitList(it1)
                    Log.d("Dashboard Data= ", it1.toString())}
                binding.btnFreeVideo.setTextColor(Color.parseColor("#4B839A"))
                binding.btnYourBooks.setTextColor(Color.parseColor("#232323"))
            }
        }

        binding.btnMenuHelp.setOnClickListener {
            fc.passData("Help", 0)
        }

    }

    private fun setImageInSlider() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = sliderImage.sliderImage().awaitResponse()
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        response.body()?.data?.let {
                            binding.loader.layoutLoader.visibility = View.GONE
                            val adapter = SliderImageAdapter()
                            adapter.renewItems(it)
                            binding.imageSlider.setSliderAdapter(adapter)
                            binding.imageSlider.isAutoCycle = true
                            binding.imageSlider.startAutoCycle()
                        }
                    }
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,"Internet Connection is not stable!!",Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.d("Slider Image", "Loading slider images")
    }

    private fun setDashBoardData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = dashBoardData.dashBoardData("Bearer $token").awaitResponse()
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        response.body()?.data.let{
                            booksData = it
                        }
                        response.body()?.data?.your_book.let {
                            adapterYourBook = YourBooksAdapter(this@DashboardFragment)
                            binding.rvYourBooks.adapter = adapterYourBook
                            it?.let { it1 -> adapterYourBook.submitList(it1) }
                        }
                        //Log.d("Dashboard Data Y= ", response.body()?.data?.your_book.toString())
                    }
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,"Internet Connection is not stable!!",Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.d("Dashboard Data", "Loading Dashboard Data")
    }

    private fun allBooksData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = allBooks.allBooksData().awaitResponse()
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        response.body()?.data.let {
                            binding.recyclerViewBookListStore.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val adapter = AllBooksAdapter(this@DashboardFragment)
                            binding.recyclerViewBookListStore.adapter = adapter
                            it?.let { it1 -> adapter.submitList(it1) }
                        }
                        //Log.d("Dashboard Data= ", response.body()?.data.toString())
                    }
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,"Internet Connection is not stable!!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadProfile(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            token = sharedPreferences.getString("token", "")!!

        }
        else{
            val intent = Intent(this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }
        Log.d("Dashboard", token)
    }

    private fun recentView(){

        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("RecentView", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            val recentSet: MutableSet<String> = sharedPreferences.getStringSet("data", null) as MutableSet<String>

            val topics: MutableList<BookDashboardData.Data.ChapterDetail.Topic> = ArrayList()
//            topics.add(BookDashboardData.Data.ChapterDetail.Topic("https://engineeringsmartbook.com/storage/topics/dtMAXUETJo62uJ1BymIfZjLkvcnGndy8zoAoqtTr.png", "New topics", 105, "Busbar differential protection"))
//            topics.add(BookDashboardData.Data.ChapterDetail.Topic("https://engineeringsmartbook.com/storage/topics/dtMAXUETJo62uJ1BymIfZjLkvcnGndy8zoAoqtTr.png", "New topics", 105, "Busbar differential protection"))
//            topics.add(BookDashboardData.Data.ChapterDetail.Topic("https://engineeringsmartbook.com/storage/topics/dtMAXUETJo62uJ1BymIfZjLkvcnGndy8zoAoqtTr.png", "New topics", 105, "Busbar differential protection"))
//            topics.add(BookDashboardData.Data.ChapterDetail.Topic("https://engineeringsmartbook.com/storage/topics/dtMAXUETJo62uJ1BymIfZjLkvcnGndy8zoAoqtTr.png", "New topics", 105, "Busbar differential protection"))

            for (i in recentSet){

                Log.d("Recent View= ", i)
                val item  = i.split('œ')
               // Log.d("Recent View Id= ", item[0])
                topics.add(BookDashboardData.Data.ChapterDetail.Topic(item[1], "", item[0].toInt(), item[2]))
            }
//
//            //Toast.makeText( context, rows.toString(), Toast.LENGTH_SHORT).show()
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            linearLayoutManager.reverseLayout = true
            binding.rvRecentView.layoutManager = linearLayoutManager
            recentViewAdapter = RecentTopicsAdapter(this)
            recentViewAdapter.submitList(topics)
            binding.rvRecentView.adapter = recentViewAdapter

        }
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

    override fun onBookClickListener(result: AllBooksDataModel.Data) {
        val intent = Intent(context, BookDetails::class.java)
        intent.putExtra("id", result.id)
        startActivity(intent)
        activity?.finish()
    }

    override fun onYourBookClickListener(result: DashboardData.Data.DashboardBookDataModel) {
        fc.passData("accessBook", result.id)
    }

    override fun onTopicsClickListener(id: Int) {
        binding.loader.layoutLoader.visibility = View.VISIBLE
        getLink(id)
    }

}