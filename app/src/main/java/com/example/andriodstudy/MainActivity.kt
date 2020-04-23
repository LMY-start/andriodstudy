package com.example.andriodstudy

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        title = "main"
        setContentView(R.layout.activity_main)

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(MessageReceiver(), filter)

        toListView.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        music.setOnClickListener {
            startActivity(Intent(this, NewMusicActivity::class.java))
        }

        callButton.setOnClickListener {
            val parse = Uri.parse("tel:13311112222")
            println("parse=======${parse}")
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    Array(1) { Manifest.permission.CALL_PHONE },
                    1
                )
            }
            startActivity(Intent(Intent.ACTION_CALL, parse))
        }

        msgButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "开启读取短信的权限", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, MessageActivity::class.java))
            }
        }

        nextButton.setOnClickListener {
            val bundle = Bundle()
            println("=========next")
            bundle.putSerializable("person", Person("lisi", 23, "tx"))
            Intent(this, SecondActivity::class.java).also { myIntent ->
                myIntent.putExtras(bundle)
                startActivityForResult(myIntent, 100)
            }
        }

        toFrameButton.setOnClickListener {
            startActivity(Intent(this, FragmentActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                resultText.text = data?.getStringExtra("result")
            }
        }
    }
}


data class Person(val name: String, val age: Int, val photo: String) : Serializable