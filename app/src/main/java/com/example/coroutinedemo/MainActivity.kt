package com.example.coroutinedemo

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var count: Int = 1

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

            override fun onStartTrackingTouch(seek: SeekBar) {
                // Не используется
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // Не используется
            }
        })
    }
}