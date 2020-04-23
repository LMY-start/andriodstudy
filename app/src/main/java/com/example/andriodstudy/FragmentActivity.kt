package com.example.andriodstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {

    var currentFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        button1.setOnClickListener {
            switchFrame(BlankFragment1())
        }
        button2.setOnClickListener {
            switchFrame(BlankFragment2())
        }
        initFrame(BlankFragment1())

    }

    private fun initFrame(blankFragment1: BlankFragment1) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, blankFragment1)
        fragmentTransaction.commit()
    }

    private fun switchFrame(targetFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (!targetFragment.isAdded) {
            currentFragment?.let {
                fragmentTransaction
                    .hide(it)
            }
            fragmentTransaction
                .add(R.id.frame, targetFragment)
                .commit()
            println("没有添加过")
        } else {
            currentFragment?.let {
                fragmentTransaction
                    .hide(it)
            }
            fragmentTransaction
                .show(targetFragment)
                .commit()
            println("已经添加过")
        }
        currentFragment = targetFragment
    }


}
