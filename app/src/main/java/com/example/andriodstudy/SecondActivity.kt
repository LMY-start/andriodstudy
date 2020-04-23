package com.example.andriodstudy

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        title = "second page"
        val person = intent.getParcelableExtra<Person>("person")

        name.text = person?.name
        age.text = person?.age.toString()
        val field = R.mipmap::class.java.getField(person?.photo.toString())
        val id = field.getInt(field)
        photo.findViewById<ImageView>(R.id.photo).setImageResource(id)
    }

    override fun finish() {
        val myIntent = Intent()
        myIntent.putExtra("result", textInput.text.toString())
        setResult(Activity.RESULT_OK, myIntent)
        super.finish()
    }

    override fun onStop() {
        super.onStop()
        println("=====Stop")
    }

    override fun onPause() {
        super.onPause()
//        val myIntent = Intent()
//        myIntent.putExtra("result", textInput.text.toString())
//        setResult(Activity.RESULT_OK, myIntent)
        println("save========== ${textInput.text}")
    }
}
