package com.example.andriodstudy

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.AssetFileDescriptor
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_music.*

class NewMusicActivity : AppCompatActivity() {
    private lateinit var afd: AssetFileDescriptor
    private lateinit var mService: MusicService
    private var isPause = false
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            println("=========onServiceConnected")
            val binder = service as MusicService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            println("=========onServiceDisconnected")
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_music)
        val fileName = "aimei.mp3"
        afd = assets.openFd(fileName)
        btnPause.isEnabled = false
        btnStop.isEnabled = false

        btnPlay.setOnClickListener {
            isPause = false
            mService.play(afd)
            btnPause.isEnabled = true
            btnStop.isEnabled = true
            btnPlay.text = "重放"
            showText.text = "正在播放音频 ${fileName}..."
        }

        btnPause.setOnClickListener {
            if (isPause) {
                isPause = false
                btnPause.text = "暂停"
                showText.text = "正在播放音频 ${fileName}..."
            } else {
                isPause = true
                btnPause.text = "继续"
                showText.text = "已暂停播放音频..."
            }
            mService.pauseOrGoOn() //暂停播放 或者 继续播放
        }

        btnStop.setOnClickListener {
            mService.stop()
            btnPlay.text = "播放"
            showText.text = "准备播放音频 ${fileName}..."
            btnPause.isEnabled = false
            btnStop.isEnabled = false
            isPause = false;
            btnPause.text = "暂停"
        }

    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, MusicService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

}
