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
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private val peopleList = ArrayList<People>()
    private var mToolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPeople()
        initProfile()
        mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)

        val adapter = PeopleAdapter(this, R.layout.plist_construction, peopleList)
        val listView:ListView = findViewById(R.id.listView)
        listView.adapter = adapter
        mToolbar.run {
            title = "Webex"
        }
        //单击打开chatActivity 控制的UI
        listView.setOnItemClickListener{ parent, view, position, id->
            val people = peopleList[position]
            val data = people.name
            val image = people.avatar.toString()
            val personId = people.personId
            val company = people.company
            val email = people.email
            val position = people.position
            var peopleBundle:PeopleBundle = PeopleBundle()
            peopleBundle.name = people.name
            peopleBundle.avatar = people.avatar.toString()
            peopleBundle.company = people.company
            peopleBundle.email = people.email
            peopleBundle.personId = people.personId
            peopleBundle.position = people.position
            Log.i("MainActivity", "Image = ${people.avatar}")
            val intentActivity = Intent(this, ChatActivity::class.java)
            intentActivity.putExtra("people_name", data)
            intentActivity.putExtra("people_avatar", image)
            intentActivity.putExtra("people_id", personId)
            intentActivity.putExtra("people_company", company)
            intentActivity.putExtra("people_email", email)
            intentActivity.putExtra("people_position", position)
            startActivity(intentActivity)
        }
        //长按创建快捷方式
        listView.setOnItemLongClickListener{ parent, view, position, id->
            val people = peopleList[position]
            val data = people.name
            val image = people.avatar
            val personId = people.personId
            val shortcutIntent = Intent(applicationContext, ChatActivity::class.java)
            val createShortCutManager = CreateShortCutManager()
            createShortCutManager.createShortcut(this,data,image,shortcutIntent)
        }
    }

    private fun initPeople(){
        var peopleCount = 0
        repeat(2){
            peopleList.add(People("Eric", R.drawable.ic_annoyed, ++peopleCount))
            peopleList.add(People("Ron", R.drawable.ic_depressed, ++peopleCount))
            peopleList.add(People("Lydia", R.drawable.ic_domestic_student, ++peopleCount))
            peopleList.add(People("Page", R.drawable.ic_international_student, ++peopleCount))
            peopleList.add(People("Darcy", R.drawable.ic_isolated, ++peopleCount))
            peopleList.add(People("Susan", R.drawable.ic_meditation, ++peopleCount))
            peopleList.add(People("Ida", R.drawable.ic_domestic_student, ++peopleCount))
            peopleList.add(People("Sally", R.drawable.box, ++peopleCount))
            peopleList.add(People("Una", R.drawable.earth, ++peopleCount))
            peopleList.add(People("Ella", R.drawable.flower, ++peopleCount))
            peopleList.add(People("Ray", R.drawable.flyer, ++peopleCount))
            peopleList.add(People("Liz", R.drawable.plastic, ++peopleCount))
            peopleList.add(People("Phil", R.drawable.sweet, ++peopleCount))
            peopleList.add(People("Reg", R.drawable.ic_annoyed, ++peopleCount))
        }
    }
    private fun initProfile(){
        for (people in peopleList){
            if (people.company == null && people.email == null && people.position == null){
                people.company = "Cisco"
                people.email = "${people.name}@cisco.com"
                people.position = "software engineer"
            }
        }
    }

}