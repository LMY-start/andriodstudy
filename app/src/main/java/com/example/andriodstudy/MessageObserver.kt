package com.example.andriodstudy

import android.app.Activity
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.text.TextUtils
import java.util.regex.Matcher
import java.util.regex.Pattern


class MessageObserver(handler: Handler) : ContentObserver(handler) {

    private lateinit var activity: Activity
    private var smsContent = ""
    private lateinit var listener: SmsListener
    private val SMS_ADDRESS_PRNUMBER = "555-6789" //短息发送提供商


    constructor(activity: Activity, handler: Handler, listener: SmsListener) : this(handler) {
        this.activity = activity
        this.listener = listener
    }

    override fun onChange(selfChange: Boolean, uri: Uri) {
        super.onChange(selfChange)
        if (uri.toString().contains("content://sms/raw")) {
            return
        }

        var cursor: Cursor? = null
        // 读取收件箱中含有某关键词的短信
        val inboxUri = Uri.parse("content://sms/inbox")
        val contentResolver = activity.contentResolver.query(
            inboxUri,
            arrayOf("_id","address", "body", "read"),
            "address =? and body like ? and read=?",
            arrayOf("+16505556789","%验证码%","0"),
            "date desc"
        )
        if (contentResolver != null) {
            contentResolver.moveToFirst()
            if (contentResolver.moveToFirst()) {
                val smsbody: String = contentResolver.getString(contentResolver.getColumnIndex("body"))
                println("++++++++++++++= received msg:   ${smsbody}")
                val regEx = "[^0-9]"
                val p: Pattern = Pattern.compile(regEx)
                val m: Matcher = p.matcher(smsbody)
                smsContent = m.replaceAll("").trim()
                if (!TextUtils.isEmpty(smsContent)) {
                    listener.onResult(smsContent)
                }
            }
        }
        contentResolver?.close()
    }

    interface SmsListener {
        fun onResult(smsContent: String?)
    }
}