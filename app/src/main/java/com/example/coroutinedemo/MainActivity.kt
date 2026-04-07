package com.example.coroutinedemo

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var count: Int = 1
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSeekBar()
    }

    private fun setupSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                count = if (progress == 0) 1 else progress
                binding.countText.text = "${count} coroutines"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })
    }

    suspend fun performTask(taskNumber: Int): Deferred<String> =
        coroutineScope.async(Dispatchers.Main) {
            delay(5_000)
            return@async "Finished Coroutine ${taskNumber}"
        }

    fun launchCoroutines(view: View) {
        (1..count).forEach {
            binding.statusText.text = "Started Coroutine ${it}"
            coroutineScope.launch(Dispatchers.Main) {
                binding.statusText.text = performTask(it).await()
            }
        }
    }
}