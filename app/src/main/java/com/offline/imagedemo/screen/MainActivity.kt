package com.offline.imagedemo.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cute.connection.api.UrlViewState
import com.cute.connection.extensions.showToast
import com.offline.imagedemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterClickListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var imageAdapter: ImageUrlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageAdapter = ImageUrlAdapter(this)
        binding.imageRecycler.apply {
            adapter = imageAdapter
            setHasFixedSize(true)
        }
        binding.button.setOnClickListener {
            val imageDetail = binding.textView.text.toString()
            if (imageDetail.isNotEmpty()) viewModel.getLargeImage(imageDetail)
        }
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UrlViewState.Success -> {
                            imageAdapter.addImage(uiState.responseModel)
                            Timber.e("QuoteViewState.Success")
                        }

                        is UrlViewState.Error -> {
                            Timber.e("QuoteViewState.Error")
                            this@MainActivity.showToast("Error...")
                        }

                        is UrlViewState.Empty -> {
                            Timber.e("QuoteViewState.Empty")
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun onImageClick(position: Int) {}

}