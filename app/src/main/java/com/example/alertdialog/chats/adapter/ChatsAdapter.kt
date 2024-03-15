package com.example.alertdialog.chats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alertdialog.R
import com.example.alertdialog.chats.model.ChatModel

class ChatsAdapter : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    private val items = ArrayList<ChatModel>()
    private var longClickListener: OnChatItemLongClickListener? = null

    fun updateItems(items: List<ChatModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    fun setOnChatItemLongClickListener(listener: OnChatItemLongClickListener) {
        longClickListener = listener
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        private val lastMessageTextView = itemView.findViewById<TextView>(R.id.lastMessageTextView)
        private val avatar_imageView = itemView.findViewById<ImageView>(R.id.avatar_imageView)

        fun bind(item: ChatModel) {
            titleTextView.text = item.title
            lastMessageTextView.text = item.lastMessage
            avatar_imageView.setImageResource(item.avatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnLongClickListener {
            longClickListener?.onItemLongClick(item)
            true
        }
    }

    interface OnChatItemLongClickListener {
        fun onItemLongClick(item: ChatModel)
    }


}



