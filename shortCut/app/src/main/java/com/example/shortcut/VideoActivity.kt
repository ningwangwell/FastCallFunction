package com.example.shortcut

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class VideoActivity : AppCompatActivity() {
    private var mToolbar: Toolbar? = null
    private var extraData: String? = null
    private var extraImage: String? = null
    private lateinit var avatarIv: ImageView
    private lateinit var leave: ImageView
    private val TAG = "CallActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        extraData = intent.getStringExtra("people_name")
        extraImage = intent.getStringExtra("people_avatar")
        Log.i("CallActivity", "onCreate")
        Log.i("CallActivity", "extraData = $extraData")
        mToolbar = findViewById(R.id.toolbar)
        avatarIv = findViewById(R.id.iv_lobby_avatar)
        val avatar1 = extraImage?.toInt()
        if (avatar1 != null) {
            avatarIv.setImageResource(avatar1)
        }
        setupActionBar()
        leave = findViewById(R.id.btn_lobby_leave)
        leave.setOnClickListener {
            finish()
        }
    }

    private fun setupActionBar() {
        mToolbar = findViewById<Toolbar>(R.id.toolbar)
        mToolbar?.title = extraData
        mToolbar?.setNavigationIcon(R.drawable.ic_back)
        mToolbar?.navigationContentDescription = "cancel"
        mToolbar?.setNavigationOnClickListener { finish() }

    }

}