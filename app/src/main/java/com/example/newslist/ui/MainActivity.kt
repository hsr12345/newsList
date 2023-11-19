package com.example.newslist.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newslist.R
import com.example.newslist.data.db.News
import com.example.newslist.data.db.NewsViewModel
import com.example.newslist.data.dto.Articles
import com.example.newslist.databinding.ActivityMainBinding
import com.example.newslist.util.LoadingDialog
import com.example.newslist.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private val newsViewModel: NewsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                NewsViewModel(application) as T
        }
    }
    private var context: Context = this

    private lateinit var newsListAdapter: NewsListAdapter
    private lateinit var dialog: LoadingDialog
    private var listMaxSize = 0

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main)
            .also {
                it.lifecycleOwner = this
                it.viewModel = viewModel
            }

        dialog = LoadingDialog(this@MainActivity)
        dialog.show()

        //network
        viewModel.data.observe(this) {
            if (it.status == "ok") {
                //db 저장
                val newsList = it.articles
                listMaxSize = newsList.size
                var listData: List<News?> = listOf()
                val t = mutableListOf<News>()
                GlobalScope.launch {
                    for (i: Int in newsList.indices) {
                        val bitmap = Util.convertBitmapFromURL(newsList[i].urlToImage)
                        val news = News(
                            i,
                            newsList[i].title,
                            newsList[i].url,
                            bitmap,
                            newsList[i].publishedAt,
                            false
                        )
                        t.add(news)
                        listData = t
                    }

                    newsViewModel.insertAll(listData)
                }

                makeList(null, newsList)
            } else {
                if (newsViewModel.getAll().value!!.isNotEmpty()) {
                    makeList(newsViewModel.getAll().value!!, null)
                } else {
                    Toast.makeText(context, "Data가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //db값
        newsViewModel.getAll().observe(this) { news ->
            if (news.isNotEmpty()) {
                if(listMaxSize == 0){
                    makeList(news, null)
                }

            } else {
                if (Util.checkNetworkState(this)) {
                    viewModel.readData()
                } else {
                    Toast.makeText(context, "Network를 확인 해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun makeList(news: List<News?>?, articles: List<Articles>?) {
        dialog.dismiss()
        newsListAdapter =
            NewsListAdapter(news, articles, context, object : NewsListAdapter.OnItemClick {
                override fun onItemClick(position: Int) {
                    var url = ""
                    url = if (news != null) {
                        val newsInfo = News(
                            position,
                            news[position]!!.title,
                            news[position]!!.url,
                            news[position]!!.urlToImage,
                            news[position]!!.publishedAt,
                            true
                        )
                        newsViewModel.update(newsInfo)
                        news[position]!!.url
                    }else{

                        articles?.get(position)!!.url
                    }

                    newsListAdapter.notifyItemChanged(position)
                    val intent = Intent(this@MainActivity, BaseWebView::class.java)
                    intent.putExtra("url", url)
                    startActivity(intent)
                }
            })

        binding.recyclerview.run {
            setHasFixedSize(true)
            adapter = newsListAdapter
            val recyclerManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            layoutManager = recyclerManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listMaxSize = 0
    }
}
