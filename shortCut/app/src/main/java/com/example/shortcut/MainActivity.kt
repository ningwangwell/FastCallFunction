package com.example.shortcut

import android.content.Intent
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
        listView.setOnItemClickListener{ parent, view, position, id->
            val people = peopleList[position]
            val data = people.name
            val image = people.avatar.toString()
            Log.i("MainActivity", "Image = ${people.avatar}")
            val intentActivity = Intent(this, ChatActivity::class.java)
            intentActivity.putExtra("people_name",data)
            intentActivity.putExtra("people_avatar",image)
            startActivity(intentActivity)
        }
    }

    private fun initPeople(){
        repeat(2){
            peopleList.add(People("Eric", R.drawable.ic_annoyed))
            peopleList.add(People("Ron", R.drawable.ic_depressed))
            peopleList.add(People("Lydia", R.drawable.ic_domestic_student))
            peopleList.add(People("Page", R.drawable.ic_international_student))
            peopleList.add(People("Darcy", R.drawable.ic_isolated))
            peopleList.add(People("Susan", R.drawable.ic_meditation))
            peopleList.add(People("Ida", R.drawable.ic_domestic_student))
            peopleList.add(People("Sally", R.drawable.ic_domestic_student))
            peopleList.add(People("Una", R.drawable.ic_domestic_student))
            peopleList.add(People("Ella", R.drawable.ic_domestic_student))
            peopleList.add(People("Ray", R.drawable.ic_domestic_student))
            peopleList.add(People("Liz", R.drawable.ic_domestic_student))
            peopleList.add(People("Phil", R.drawable.ic_domestic_student))
            peopleList.add(People("Reg", R.drawable.ic_domestic_student))
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