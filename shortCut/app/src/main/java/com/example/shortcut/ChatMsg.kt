package com.example.shortcut

class ChatMsg(val content: String , val type: Int) {
    companion object{
        const val TYPE_RECEIVED = 0
        const val TYPE_SEND = 1
    }
}