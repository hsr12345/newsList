package com.example.newslist.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.DisplayMetrics
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


object Util {
    fun convertBitmapFromURL(url: String?): Bitmap?{
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val input = connection.inputStream

            return BitmapFactory.decodeStream(input)
        }catch (e: IOException){

        }
        return null
    }


    fun checkNetworkState(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(ConnectivityManager::class.java)
        val network: Network = connectivityManager.activeNetwork ?: return false
        val actNetwork: NetworkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }

    fun getScreenWidth(context: Context): Int {
        val metrics = context.resources.displayMetrics
        return metrics.widthPixels
    }

    fun px2dp(px: Int, context: Context): Int {
        val resources: Resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }
}