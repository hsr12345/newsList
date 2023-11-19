package com.example.newslist.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newslist.R
import com.example.newslist.data.db.News
import com.example.newslist.data.dto.Articles
import com.example.newslist.util.Util


class NewsListAdapter(private var list: List<News?>?, private var articles: List<Articles?>?, private var context: Context
                      , private var onItemListener: OnItemClick) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private var firstRow = 6

    interface OnItemClick{
        fun onItemClick(position: Int)
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        firstRow = if(list == null){
            articles!!.size / 3
        }else{
            list!!.size / 3
        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        val width = Util.px2dp(Util.getScreenWidth(context), context)
        return if(width >= 600){
            3
        }else{
            1
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(itemCount > 1){
            threeRow(holder, position)
        }else{
            val size = if(list == null){
                articles!!.size
            }else{
                list!!.size
            }
            rowUi(holder, position, 0,size)
        }
    }

    private fun threeRow(holder: ViewHolder, position: Int){
        val size = if(list == null){
            articles!!.size
        }else{
            list!!.size
        }
        when (position) {
            0 -> {
                rowUi(holder, position, 0, firstRow)
                holder.parentLayout.setBackgroundColor(Color.parseColor("#cccccc"))
            }
            1 -> {
                rowUi(holder, position, firstRow, firstRow *2)
                holder.parentLayout.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            }
            2 -> {
                rowUi(holder, position, firstRow *2, size)
                holder.parentLayout.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }
    }

    private fun rowUi(holder: ViewHolder, position: Int, start: Int, max: Int){
        for (i: Int in start until max) {
            val itemLayout = RelativeLayout(context)
            val itemLayoutParams = RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT
                ,LayoutParams.WRAP_CONTENT
            )
            itemLayout.layoutParams = itemLayoutParams

            //thumbnail
            val image = ImageView(context)
            image.id = i+1
            val imageParams = LayoutParams(
                200
                ,200
            )

            if(list != null){
                if(list!![i]!!.urlToImage != null){
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
                    Glide.with(context).load(list!![i]?.urlToImage).apply(requestOptions).error(R.color.purple_200).into(image)
                }
            }else{
                if(articles!![i]!!.urlToImage != null){
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
                    Glide.with(context).load(articles!![i]?.urlToImage).apply(requestOptions).error(R.color.purple_200).into(image)
                }
            }


            image.layoutParams = imageParams

            val isSelect = if(list == null){
                articles?.get(i)?.isSelect
            }else{
                list!![i]?.isSelect
            }

            val titleData = if(list == null){
                articles?.get(i)?.title
            }else{
                list!![i]?.title
            }

            val publishedAt = if(list == null){
                articles?.get(i)?.publishedAt
            }else{
                list!![i]?.publishedAt
            }

            //text area
            val textLayout = RelativeLayout(context)
            val textLayoutParams = RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT
                ,LayoutParams.WRAP_CONTENT
            )
            textLayoutParams.addRule(RelativeLayout.END_OF, image.id)
            textLayoutParams.leftMargin = 20
            textLayout.layoutParams = textLayoutParams

            val title = TextView(context)
            title.id = i+1
            title.text = titleData
            title.textSize = 18F
            val titleParams = RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT
                ,LayoutParams.WRAP_CONTENT
            )
            title.layoutParams = titleParams
            if(isSelect == true){
                Log.e("test", "1")
                title.setTextColor(Color.RED)
            }else{
                title.setTextColor(Color.BLACK)
            }

            val date = TextView(context)
            date.text = publishedAt
            date.textSize = 14F
            val dateParams = RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT
                ,LayoutParams.WRAP_CONTENT
            )
            dateParams.addRule(RelativeLayout.BELOW, title.id)
            dateParams.topMargin = 15
            date.layoutParams = dateParams
            date.setTextColor(Color.BLACK)

            textLayout.addView(title)
            textLayout.addView(date)

            itemLayout.addView(image)
            itemLayout.addView(textLayout)

            itemLayout.setOnClickListener {
                onItemListener.onItemClick(i)
                if(list != null){
                    list!![i]?.isSelect = true
                }else{
                    articles!![i]!!.isSelect = true
                }

            }

            holder.parentLayout.addView(itemLayout)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val parentLayout: LinearLayout = itemView.findViewById(R.id.whole)
    }
}