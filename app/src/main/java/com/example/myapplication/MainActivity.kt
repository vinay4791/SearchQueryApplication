package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    lateinit var viewModelFactory: MainViewModelFactory
    lateinit var viewModel: MainViewModel
    private val repository: MainRepository by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {

        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getUsers().observe(this, Observer<List<ResponseItem>> { users ->
            populateRecyclerView(users)
        })

        viewModel.getLoadingState().observe(this, Observer<Boolean> { loadingState ->
            updateProgressBar(loadingState)
        })

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 2) {
                    makeApiCall(s.toString())
                }
            }
        })
    }

    private fun updateProgressBar(loadingState: Boolean) {
        if (loadingState) {
            loadingView.showLoading(R.color.loader_bg_white_transparent)
        } else {
            loadingView.hideLoading()
        }
    }

    private fun populateRecyclerView(users: List<ResponseItem>) {

    }

    private fun makeApiCall(toString: String) {
        viewModel.loadResults(toString)
    }


}