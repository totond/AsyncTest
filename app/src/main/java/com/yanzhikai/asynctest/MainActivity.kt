package com.yanzhikai.asynctest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val test = object :GenericityTestx<Int,String>(){
            override fun test1(x: Array<Int>): Int {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }


}
