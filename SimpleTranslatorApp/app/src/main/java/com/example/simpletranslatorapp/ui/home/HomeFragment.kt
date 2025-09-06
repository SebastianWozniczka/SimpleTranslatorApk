package com.example.simpletranslatorapp.ui.home

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpletranslatorapp.databinding.FragmentHomeBinding
import org.json.JSONException
import java.util.Locale

class HomeFragment : Fragment(),TextToSpeech.OnInitListener {

    private var _binding: FragmentHomeBinding? = null
    private var mTts: TextToSpeech? = null
    private val binding get() = _binding!!

    lateinit var textView: TextView
    lateinit var englishTextView: TextView
    lateinit var EnglishEditText: EditText

    override fun onResume() {
        super.onResume()

        EnglishEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {

                if (s.length > 0) {
                    var txt = EnglishEditText.text.toString()
                    var text = RetrieveTas().translate(txt)

                    englishTextView.text = text
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        mTts = TextToSpeech(context, this)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        textView = binding.textHome
        englishTextView = binding.EnglishTextView
        EnglishEditText = binding.EnglishEditText

        homeViewModel.text.observe(viewLifecycleOwner) {

            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            try {

            } catch (e2: JSONException) {

                e2.printStackTrace()
            } catch (e: Exception) {

                e.printStackTrace()
            }

            var retrieveTas: RetrieveTas = RetrieveTas()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onInit(status: Int) {
        TextSpeak()


        val text = "Dom"

        mTts?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        if (!mTts!!.isSpeaking()) {
            mTts = TextToSpeech(context, this)


        }
        if (status == TextToSpeech.SUCCESS) {
            val result = mTts!!.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language not supported!")
            } else {
            }

        }
    }
}

fun TextSpeak(){

}

