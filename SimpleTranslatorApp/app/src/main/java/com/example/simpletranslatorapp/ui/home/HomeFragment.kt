package com.example.simpletranslatorapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpletranslatorapp.databinding.FragmentHomeBinding
import org.json.JSONException

class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var englishTextView: TextView
    lateinit var englishEditText: EditText

    override fun onResume() {
        super.onResume()

         englishEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                editingText(s)
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

    //adding text support
    fun editingText(s: CharSequence){

        if (s.isNotEmpty())
        {
            val txt = englishEditText.text.toString()
            val text = RetrieveTas().translate(txt)

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


