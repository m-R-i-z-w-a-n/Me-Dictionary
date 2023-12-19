package com.dictionary.me_dictionary

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dictionary.me_dictionary.adapters.MeaningsAdapter
import com.dictionary.me_dictionary.adapters.PhoneticsAdapter
import com.dictionary.me_dictionary.models.Dictionary

class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var wordView: TextView

    private lateinit var phoneticRecyclerView: RecyclerView
    private lateinit var meaningRecyclerView: RecyclerView

    private lateinit var progressDialog: ProgressDialog

    private lateinit var phoneticsAdapter: PhoneticsAdapter
    private lateinit var meaningsAdapter: MeaningsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        searchView = findViewById(R.id.search_view)
        wordView = findViewById(R.id.text_view_word)
        phoneticRecyclerView = findViewById(R.id.recycler_phonetics)
        meaningRecyclerView = findViewById(R.id.recycler_meanings)

        progressDialog = ProgressDialog(this)

        progressDialog.setTitle("Loading...")
        progressDialog.show()

        val requestManager = RequestManager(applicationContext)
        requestManager.getWordMeaning(listener, "Hello")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                progressDialog.setTitle("Fetching response for ${query}...")
                progressDialog.show()

                RequestManager(applicationContext)
                    .getWordMeaning(listener, query)
                return true
            }

        })
    }

    private val listener: OnFetchDataListener = object : OnFetchDataListener {
        override fun onFetchData(dictionary: Dictionary, message: String?) {
            progressDialog.dismiss()
            showData(dictionary)
        }

        override fun onError(message: String?) {
            Toast.makeText(applicationContext, message!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showData(dictionary: Dictionary) {
        fun setRecyclerView(view: RecyclerView) {
            view.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(applicationContext, 1)
                adapter = if (view == phoneticRecyclerView) phoneticsAdapter else meaningsAdapter
            }
        }
        wordView.text = "Word: ${dictionary.word}"

        phoneticsAdapter = PhoneticsAdapter(applicationContext, dictionary.phonetics!!)
        meaningsAdapter = MeaningsAdapter(applicationContext, dictionary.meanings!!)

        listOf(phoneticRecyclerView, meaningRecyclerView).forEach(::setRecyclerView)

    }
}