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
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private val peopleList = ArrayList<People>()
    private var mToolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPeople()
        mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)

        val adapter = PeopleAdapter(this, R.layout.plist_construction, peopleList)
        val listView:ListView = findViewById(R.id.listView)
        listView.adapter = adapter
        mToolbar.run {
            title = "ShortCutApp"
        }
        //单击打开chatActivity 控制的UI
        listView.setOnItemClickListener{ parent, view, position, id->
            val people = peopleList[position]
            val data = people.name
            val image = people.avatar.toString()
            val personId = people.personId
            Log.i("MainActivity", "Image = ${people.avatar}")
            val intentActivity = Intent(this, ChatActivity::class.java)
            intentActivity.putExtra("people_name",data)
            intentActivity.putExtra("people_avatar",image)
            intentActivity.putExtra("people_id",personId)
            startActivity(intentActivity)
        }
        //长按创建快捷方式
        listView.setOnItemLongClickListener{parent, view, position, id->
            val people = peopleList[position]
            val data = people.name
            val image = people.avatar
            val personId = people.personId
            val shortcutIntent = Intent(applicationContext, ChatActivity::class.java)
            createShortcut(this, data, image, shortcutIntent)
        }
    }

    private fun initPeople(){
        var peopleCount = 0
        repeat(2){
            peopleList.add(People("Eric", R.drawable.ic_annoyed,++peopleCount))
            peopleList.add(People("Ron", R.drawable.ic_depressed,++peopleCount))
            peopleList.add(People("Lydia", R.drawable.ic_domestic_student,++peopleCount))
            peopleList.add(People("Page", R.drawable.ic_international_student,++peopleCount))
            peopleList.add(People("Darcy", R.drawable.ic_isolated,++peopleCount))
            peopleList.add(People("Susan", R.drawable.ic_meditation,++peopleCount))
            peopleList.add(People("Ida", R.drawable.ic_domestic_student,++peopleCount))
            peopleList.add(People("Sally", R.drawable.ic_domestic_student,++peopleCount))
            peopleList.add(People("Una", R.drawable.ic_domestic_student,++peopleCount))
            peopleList.add(People("Ella", R.drawable.ic_domestic_student,++peopleCount))
            peopleList.add(People("Ray", R.drawable.ic_domestic_student,++peopleCount))
            peopleList.add(People("Liz", R.drawable.ic_domestic_student,++peopleCount))
            peopleList.add(People("Phil", R.drawable.ic_domestic_student,++peopleCount))
            peopleList.add(People("Reg", R.drawable.ic_domestic_student,++peopleCount))
        }
    }
    private fun createShortcut(activity: Activity, data: String?, image: Int, actionIntent: Intent): Boolean {
        val iconRes = Intent.ShortcutIconResource.fromContext(activity, R.drawable.ic_meditation)
        val bitmap = BitmapFactory.decodeResource(resources, image)
        Log.i("ChatActivity", "bitmap = $bitmap")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val shortcut = Intent("com.android.launcher.action.INSTALL_SHORTCUT")
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, data)
            shortcut.putExtra("duplicate", false)
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconRes)
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent)
            activity.sendBroadcast(shortcut)
            return true

        } else {
            val shortcutMsg: ShortcutManager = activity.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager
            if (null == shortcutMsg) {
                Log.e("ChatActivity", "create shortcut failed")
                return false
            }
            val mShortcutInfoBuilder = ShortcutInfo.Builder(this, data.toString())
            mShortcutInfoBuilder.setShortLabel(data.toString())
            mShortcutInfoBuilder.setLongLabel(data.toString())
            mShortcutInfoBuilder.setIcon(Icon.createWithResource(this,image))
            val shortcutIntent = Intent(applicationContext, ChatActivity::class.java)
            shortcutIntent.putExtra("people_name", data)
            shortcutIntent.action = Intent.ACTION_CREATE_SHORTCUT
            mShortcutInfoBuilder.setIntent(shortcutIntent)
            val mShortcutInfo = mShortcutInfoBuilder.build()
            val mShortcutManager = getSystemService(ShortcutManager::class.java)
            mShortcutManager.requestPinShortcut(mShortcutInfo, null)
            return true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.backup -> Toast.makeText(this,"You click Backup", Toast.LENGTH_SHORT).show()
            R.id.delete -> Toast.makeText(this,"You click delete", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this,"You click settings", Toast.LENGTH_SHORT).show()

        }
        return true
    }


}