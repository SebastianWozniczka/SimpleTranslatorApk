@file:Suppress("DEPRECATION")

package com.example.simpletranslatorapp.ui.home

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils
import org.json.JSONObject
import java.net.URLEncoder
import java.util.Locale

class RetrieveTas(){

    //This function translates text from Polish to English
    fun translate(text: String, lc: Locale): String? {
        var i:Int
        val s = Locale.getDefault().language
       i = 0
        when (s) {
            "pl" -> {
                i = 1
            }
            "ch" -> {
                i = 2
            }
            "it" -> {
                i = 3
            }
            "en" -> {
                i = 5
            }
            "fr" -> {
                i = 6
            }
        }
        val currentLanguage = when(i) {

            1 -> Locale.getDefault().language
            2 -> Locale.CHINESE.language
            3 -> Locale.ITALY.language
            5-> Locale.ENGLISH.language
            6-> Locale.FRENCH.language

            else -> Locale.JAPAN.language
        }

        val dstLanguage = lc
        val srcLanguage: String = currentLanguage

        var translated: String? = null
        try {
            val query = URLEncoder.encode(text, "UTF-8")
            val pairing = URLEncoder.encode(
                srcLanguage + "|" + dstLanguage.language,
                "UTF-8"
            )
            val url = "http://mymemory.translated.net/api/get?q=$query&langpair=$pairing"
            val hc: HttpClient = DefaultHttpClient()
            val hg = HttpGet(url)
            val hr = hc.execute(hg)
            if (hr.statusLine.statusCode == HttpStatus.SC_OK) {
                val response = JSONObject(EntityUtils.toString(hr.entity))
                translated = response.getJSONObject("responseData").getString("translatedText")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return translated
    }
}


