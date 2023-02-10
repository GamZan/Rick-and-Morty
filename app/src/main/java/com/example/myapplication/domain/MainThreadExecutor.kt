package com.example.myapplication.domain

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

internal class MainThreadExecutor : Executor {
    private val mHandler = Handler(Looper.getMainLooper())
    override fun execute(p0: Runnable?) {
        p0?.let { mHandler.post(it) }
    }
}