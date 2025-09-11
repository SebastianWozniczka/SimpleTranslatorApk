package com.example.simpletranslatorapp.ui.home

import android.os.AsyncTask
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
    fun translate(text: String): String? {

        var dstLanguage = Locale.ENGLISH
        var srcLanguage = Locale.getDefault()

        var translated: String? = null
        try {
            val query = URLEncoder.encode(text, "UTF-8")
            val langpair = URLEncoder.encode(
                srcLanguage.getLanguage() + "|" + dstLanguage.getLanguage(),
                "UTF-8"
            )
            val url = "http://mymemory.translated.net/api/get?q=" + query + "&langpair=" + langpair
            val hc: HttpClient = DefaultHttpClient()
            val hg = HttpGet(url)
            val hr = hc.execute(hg)
            if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                val response = JSONObject(EntityUtils.toString(hr.getEntity()))
                translated = response.getJSONObject("responseData").getString("translatedText")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return translated
    }


}


