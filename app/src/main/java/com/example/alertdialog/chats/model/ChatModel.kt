package com.example.alertdialog.chats.model

import androidx.annotation.DrawableRes

data class ChatModel(
    @DrawableRes val avatar: Int,
    val title:String,
    val lastMessage:String
)