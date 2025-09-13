package com.example.simpletranslatorapp.ui.home

import android.R
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
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
import androidx.annotation.RequiresApi
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

        val appLocales = AppCompatDelegate.getApplicationLocales()


        val systemLocales = LocaleList.getDefault()

        for (i in 0..<systemLocales.size()) {
            systemLocales.get(i)?.let {
                Log.i("SEBA","${it.displayLanguage} (${it.displayCountry})")
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
                        val otl: OnTouchListener = OnTouchListener { v, event ->
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
        val options: Array<String> = arrayOf("English", "French")
        val arrayAdapter = getActivity()?.let { ArrayAdapter<String>(it.getApplicationContext(), R.layout.simple_spinner_dropdown_item,options) }
        spinner.setAdapter(arrayAdapter)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var selectedText = options[position]
                if(selectedText == "English"){
                    lc = Locale.ENGLISH
                    Log.i("SEBA",lc.toString())

                }
                if(selectedText == "French"){
                    lc = Locale.FRENCH
                    Log.i("SEBA",lc.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    //adding text support
    fun editingText(s: CharSequence, lc1: Locale){

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


