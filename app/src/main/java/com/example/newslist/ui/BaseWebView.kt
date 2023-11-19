package com.example.newslist.ui

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.newslist.R
import com.example.newslist.databinding.ActivityWebviewBinding

class BaseWebView : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)

        url = intent.getStringExtra("url").toString()

        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(){
        binding.webview.settings.javaScriptEnabled = true

        binding.webview.webViewClient = object : WebViewClient() {
            private fun startMarket(intent: Intent?): Boolean {
                val marketIntent = Intent(Intent.ACTION_VIEW)
                marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                marketIntent.data = Uri.parse("market://details?id=" + intent!!.getPackage())
                startActivity(marketIntent)
                return true
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val url = request.url.toString()
                return if (url.startsWith("intent:") || url.startsWith("market:")) {
                    var intent: Intent? = null
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        val existPackage =
                            packageManager.getLaunchIntentForPackage(intent.getPackage()!!)
                        if (existPackage != null) {
                            startActivity(intent)
                            true
                        } else {
                            startMarket(intent)
                        }
                    } catch (e: ActivityNotFoundException) {
                        intent?.let { startMarket(it) } ?: true
                    } catch (e: Exception) {
                        true
                    }
                } else {
                    super.shouldOverrideUrlLoading(view, request)
                }
            }
        }


        binding.webview.loadUrl(url)
    }

    override fun onBackPressed() {
        finish()
    }
}