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

        val fragment1 = BlankFragment1.newInstance("null", "null")

        button1.setOnClickListener {
            switchFrame(fragment1)
        }

        val fragment2 = BlankFragment2.newInstance("null", "null")
        button2.setOnClickListener {
            switchFrame(fragment2)
        }
        currentFragment = fragment1
        initFrame(currentFragment as BlankFragment1)

    }

    private fun initFrame(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
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
                .addToBackStack(null)  // 可以通过返回按钮返回上一个fragment
                .commit()
            println("没有添加过")
        } else {
            currentFragment?.let {
                fragmentTransaction
                    .hide(it)
            }
            fragmentTransaction
                .show(targetFragment)
                .addToBackStack(null)
                .commit()
            println("已经添加过")
        }
        currentFragment = targetFragment
    }


}
