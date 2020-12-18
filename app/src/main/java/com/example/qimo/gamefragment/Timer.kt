package com.example.qimo.gamefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class Timer () {
    companion object {
        var second = 0
        var running = false
        var textView_time="00:00:00"
        fun runTimer(){
            val handler = Handler()
            val runnable = object : Runnable{
                override fun run() {
                    val hours = second / 3600
                    val minutes = (second % 3600) / 60
                    val secs = second % 60
                    textView_time = String.format("%02d:%02d:%02d", hours, minutes, secs)
                    if (running) {
                        second++
                    }
                    handler.postDelayed(this, 1000)
                }
            }
            handler.post(runnable)
        }
    }

    }



