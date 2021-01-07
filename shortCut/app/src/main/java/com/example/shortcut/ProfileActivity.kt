package com.example.shortcut

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity: AppCompatActivity(){
    private var extraData:String? = null
    private var extraImage:String? = null
    private var extraId :Int = 0
    private var extraCompany:String? = null
    private var extraEmail:String? = null
    private var extraPosition:String? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_view)
        val avatar: ImageView = findViewById(R.id.profile_people_avatar)
        val name: TextView = findViewById(R.id.profile_people_name)
        val id: TextView = findViewById(R.id.profile_people_id)
        val email: TextView = findViewById(R.id.profile_people_email)
        val company: TextView = findViewById(R.id.profile_people_company)
        val position: TextView = findViewById(R.id.profile_people_position)

        extraData = intent.getStringExtra("people_name")
        extraImage = intent.getStringExtra("people_avatar")
        extraId = intent.getIntExtra("people_id",0)
        extraCompany = intent.getStringExtra("people_company")
        extraEmail = intent.getStringExtra("people_email")
        extraPosition = intent.getStringExtra("people_position")
        val avatar1 = extraImage?.toInt()
        if (avatar1 != null) {
            avatar.setImageResource(avatar1)
        }
        name.text = "$extraData"
        id.text = "people id : $extraId"
        email.text = "email : $extraEmail"
        company.text = "company : $extraCompany"
        position.text = "position : $extraPosition"

    }

}