package com.example.andriodstudy

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_meaasge.*

class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaasge)
        title = "ReadMessage"
        getMsgbButton.setOnClickListener {

            val messageObserver = MessageObserver(
                this, Handler(),
                object : MessageObserver.SmsListener {
                    override fun onResult(smsContent: String?) {
                        msgView.text = smsContent
                    }
                })

            this.contentResolver.registerContentObserver(
                Uri.parse("content://sms/"),
                true,
                messageObserver
            )

            getMsgbButton.text = "已开启"

        }

    }
}

