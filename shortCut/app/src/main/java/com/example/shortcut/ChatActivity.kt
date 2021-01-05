package com.example.shortcut

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

public class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"
    private val msgList = ArrayList<ChatMsg>()
    private var msgAdapter: ChatMsgAdapter? = null
    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_view)
        val extraData = intent.getStringExtra("people_name")
        val extraImage = intent.getStringExtra("people_avatar")
        val extraId = intent.getIntExtra("people_id",0)
        initMsg(extraData.toString())
        val send: Button = findViewById(R.id.send)
        var inputText: EditText = findViewById(R.id.inputText)
        val shortCut: ImageButton = findViewById(R.id.shortcut)
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

        shortCut.setOnClickListener {
            Toast.makeText(this, "You click shortcut", Toast.LENGTH_SHORT).show()
            val shortcutIntent = Intent(applicationContext, ChatActivity::class.java)
            createShortcut(this, extraData, extraImage, shortcutIntent)
        }
    }

    private fun createShortcut(activity: Activity, data: String?, image: String?, actionIntent: Intent) {
        val iconRes = Intent.ShortcutIconResource.fromContext(activity, R.drawable.ic_meditation)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.message_left)
        Log.i("ChatActivity", "bitmap = $bitmap")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val shortcut = Intent("com.android.launcher.action.INSTALL_SHORTCUT")
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, data)
            shortcut.putExtra("duplicate", false)
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconRes)
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent)
            activity.sendBroadcast(shortcut)

        } else {
            val shortcutMsg: ShortcutManager = activity.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager
            if (null == shortcutMsg) {
                Log.e("ChatActivity", "create shortcut failed")
                return
            }
            val mShortcutInfoBuilder = ShortcutInfo.Builder(this, data.toString())
            mShortcutInfoBuilder.setShortLabel(data.toString())
            mShortcutInfoBuilder.setLongLabel(data.toString())
            mShortcutInfoBuilder.setIcon(Icon.createWithResource(this, R.mipmap.people))
            val shortcutIntent = Intent(applicationContext, ChatActivity::class.java)
            shortcutIntent.putExtra("people_name", data)
            shortcutIntent.action = Intent.ACTION_CREATE_SHORTCUT
            mShortcutInfoBuilder.setIntent(shortcutIntent)
            val mShortcutInfo = mShortcutInfoBuilder.build()
            val mShortcutManager = getSystemService(ShortcutManager::class.java)
            mShortcutManager.requestPinShortcut(mShortcutInfo, null)


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
            R.id.backup -> Toast.makeText(this, "You click Backup", Toast.LENGTH_SHORT).show()
            R.id.delete -> Toast.makeText(this, "You click delete", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "You click settings", Toast.LENGTH_SHORT).show()

        }
        return true
    }

}