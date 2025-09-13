package com.example.simpletranslatorapp.ui.home

//noinspection SuspiciousImport
import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.LocaleList
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpletranslatorapp.databinding.FragmentHomeBinding
import org.json.JSONException
import java.util.Locale

class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var englishTextView: TextView
    lateinit var englishEditText: EditText
    lateinit var spinner: Spinner
    lateinit var lc:Locale



    override fun onResume() {
        super.onResume()

        val systemLocales = LocaleList.getDefault()

        for (i in 0..<systemLocales.size()) {
            systemLocales.get(i)?.let {
            }
        }
                  spinnerSettings();

                  englishEditText.addTextChangedListener(object : TextWatcher {

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }
                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        editingText(s,lc)
                    }

                    @SuppressLint("ClickableViewAccessibility")
                    override fun afterTextChanged(s: Editable) {


                        //edittext input handling
                        val otl = OnTouchListener { v, event ->
                            val inType = englishEditText.inputType
                            englishEditText.inputType = InputType.TYPE_NULL
                            englishEditText.onTouchEvent(event)
                            englishEditText.inputType = inType


                            true
                        }
                    }
                }
                )
    }
    private fun spinnerSettings() {
        spinner = binding.spinner1!!
        val options: Array<String> = arrayOf("English", "French","German","Italian", "Chinese", "British","Japanese", "Taiwanese","Canadian")
        val arrayAdapter = activity?.let { ArrayAdapter(it.applicationContext, R.layout.simple_spinner_item,options) }
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedText = options[position]
                if(selectedText == "English"){
                    lc = Locale.ENGLISH
                }
                if(selectedText == "French"){
                    lc = Locale.FRENCH
                }
                if(selectedText == "German"){
                    lc = Locale.GERMAN
                }
                if(selectedText == "Italian"){
                    lc = Locale.ITALY
                }

                if(selectedText == "Chinese"){
                    lc = Locale.CHINESE
                }
                if(selectedText == "British"){
                    lc = Locale.UK
                }
                if(selectedText == "Japanese"){
                    lc = Locale.JAPAN
                }
                if(selectedText == "Taiwanese"){
                    lc = Locale.TAIWAN
                }
                if(selectedText ==  "Canadian"){
                    lc = Locale.CANADA
                }





            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    //adding text support
    fun editingText(s: CharSequence, lc: Locale){

        if (s.isNotEmpty())
        {
            val txt = englishEditText.text.toString()
            val text = RetrieveTas().translate(txt,lc)

            englishTextView.text = text
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        englishTextView = binding.EnglishTextView
        englishEditText = binding.EnglishEditText


        homeViewModel.text.observe(viewLifecycleOwner) {

            //exception checking
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            try {

            } catch (e2: JSONException) {

                e2.printStackTrace()
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


