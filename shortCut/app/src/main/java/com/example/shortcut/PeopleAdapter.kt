package com.example.shortcut

import android.app.Activity
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeopleAdapter(activity: Activity,val resourceId:Int , data: List<People>):ArrayAdapter<People>(activity,resourceId,data){
    inner class ViewHolder(val avatar:ImageView, val name:TextView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder:ViewHolder
        val view:View
        if (convertView == null) {
            view =  LayoutInflater.from(context).inflate(resourceId,parent,false)
            val avatar:ImageView = view.findViewById(R.id.avatar)
            val name:TextView = view.findViewById(R.id.name)
            viewHolder = ViewHolder(avatar,name)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }


        val people = getItem(position)
        if (people != null){
            viewHolder.avatar.setImageResource(people.avatar)
            viewHolder.name.text = people.name
        }
        return view
    }

}