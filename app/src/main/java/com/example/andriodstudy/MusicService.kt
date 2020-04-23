package com.example.andriodstudy

import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder


class MusicService : Service() {

    private lateinit var player: MediaPlayer
    private val binder = LocalBinder()


    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        println("==================MusicService onCreate")
        player = MediaPlayer()
    }

    fun play(afd: AssetFileDescriptor) {
        println("==================MusicService play")
        player.reset()
        player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        player.prepare() //预加载音频
        player.start() //开始播放
    }

    fun stop() {
        println("==================MusicService stop")
        player.stop()
    }

    fun pauseOrGoOn() {
        println("==================MusicService pause")
        if (player.isPlaying) {
            player.pause() //暂停播放
        } else {
            player.start() //继续播放
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("==================MusicService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        println("==================MusicService onBind")
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        println("==================MusicService onUnbind")
//        player.stop()
        return true
    }

    override fun onRebind(intent: Intent) {
        println("==================MusicService onRebind")
    }

    override fun onDestroy() {
        println("=================MusicService onDestroy")
    }
}
