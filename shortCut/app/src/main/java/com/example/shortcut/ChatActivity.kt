package com.example.shortcut

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

public class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"
    private val msgList = ArrayList<ChatMsg>()
    private var msgAdapter: ChatMsgAdapter? = null
    private var mToolbar: Toolbar? = null
    private var extraData:String? = null
    private var extraImage:String? = null
    private var extraId :Int = 0
    private var extraCompany:String? = null
    private var extraEmail:String? = null
    private var extraPosition:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_view)
        extraData = intent.getStringExtra("people_name")
        extraImage = intent.getStringExtra("people_avatar")
        extraId = intent.getIntExtra("people_id",0)
        extraCompany = intent.getStringExtra("people_company")
        extraEmail = intent.getStringExtra("people_email")
        extraPosition = intent.getStringExtra("people_position")
        initMsg(extraData.toString())
        val send: Button = findViewById(R.id.send)
        var inputText: EditText = findViewById(R.id.inputText)
        mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)

        Log.i(TAG, "people name = $extraData,people avatar = $extraImage,people id =$extraId")
        mToolbar.run {
            title = extraData
        }
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        msgAdapter = ChatMsgAdapter(msgList)
        recyclerView.adapter = msgAdapter
        send.setOnClickListener {
            val content = inputText.text.toString()
            if (content.isNotEmpty()) {
                val msg = ChatMsg(content, ChatMsg.TYPE_SEND)
                msgList.add(msg)
                msgAdapter!!.notifyItemInserted(msgList.size - 1)
                recyclerView.scrollToPosition(msgList.size - 1)
                inputText.setText("")
            }
        }


    }


    private fun initMsg(name: String) {
        val msg1 = ChatMsg("Hello guy.", ChatMsg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = ChatMsg("Hello, Who is that ?", ChatMsg.TYPE_SEND)
        msgList.add(msg2)
        val msg3 = ChatMsg("This is $name . Nice talking to you", ChatMsg.TYPE_RECEIVED)
        msgList.add(msg3)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                val intentActivity = Intent(this, ProfileActivity::class.java)
                intentActivity.putExtra("people_name",extraData)
                intentActivity.putExtra("people_avatar",extraImage)
                intentActivity.putExtra("people_id",extraId)
                intentActivity.putExtra("people_company",extraCompany)
                intentActivity.putExtra("people_email",extraEmail)
                intentActivity.putExtra("people_position",extraPosition)
                startActivity(intentActivity)
            }

            R.id.delete -> Toast.makeText(this, "You click delete", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "You click settings", Toast.LENGTH_SHORT).show()

        }
        return true
    }

}