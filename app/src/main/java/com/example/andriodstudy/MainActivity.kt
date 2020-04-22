package com.example.andriodstudy

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.INotificationSideChannel
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        title = "main"
        setContentView(R.layout.activity_main)

        toListView.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        callButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1355555555")))
        }

        msgButton.setOnClickListener {
            Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:1355555555")).also { myIntent ->
                myIntent.putExtra("sms_body", "smsBodyXXXXXXXXXXX")
                startActivity(myIntent);
            }
        }

        nextButton.setOnClickListener {
            val bundle = Bundle()
            println("=========next")
            bundle.putParcelable("person", Person("lisi", 23, "tx"))
            Intent(this, SecondActivity::class.java).also { myIntent ->
                myIntent.putExtras(bundle)
                startActivityForResult(myIntent, 100)
            }
        }

        toFrameButton.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("======== result: ${data}")
        // Check which request we're responding to
        if (requestCode == 100) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                resultText.text = data?.getStringExtra("result")
                // Do something with the contact here (bigger example below)
            }
        }
    }


}

data class Person(val name: String, val age: Int, val photo: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeInt(age)
        dest.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}