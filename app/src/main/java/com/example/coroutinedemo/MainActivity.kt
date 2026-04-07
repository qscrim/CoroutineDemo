package com.example.coroutinedemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
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

    companion object {
        private const val TAG = "CoroutineDemo"
    }

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
        coroutineScope.async(Dispatchers.IO) {
            delay(5_000)
            return@async "Finished Coroutine ${taskNumber}"
        }

    fun launchCoroutines(view: View) {
        if (count > 500) {
            Toast.makeText(this, "Большое количество корутин! Проверь Logcat", Toast.LENGTH_SHORT).show()
        }

        Log.d(TAG, "Запуск $count корутин")

        (1..count).forEach {
            binding.statusText.text = "Started Coroutine ${it}"
            coroutineScope.launch(Dispatchers.Main) {
                try {
                    binding.statusText.text = performTask(it).await()
                    Log.d(TAG, "Coroutine ${it} завершена")
                } catch (e: Exception) {
                    Log.e(TAG, "Ошибка в корутине ${it}: ${e.message}")
                }
            }
        }
    }
}