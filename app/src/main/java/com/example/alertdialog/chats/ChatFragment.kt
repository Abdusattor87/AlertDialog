package com.example.alertdialog.chats

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.alertdialog.R
import com.example.alertdialog.chats.adapter.ChatsAdapter
import com.example.alertdialog.chats.model.ChatModel

class ChatFragment : Fragment(R.layout.fragment_chats) {

    private val recyclerView by lazy { requireView().findViewById<RecyclerView>(R.id.recyclerView) }
    private val adapter = ChatsAdapter()


    private val data = mutableListOf(
        ChatModel(R.drawable.ic_bryin,"Bryan", "What do you think?"),
        ChatModel(R.drawable.cindy_image,"Kari", "Looks great!"),
        ChatModel(R.drawable.ic_diana,"Diana", "Lunch on Monday?"),
        ChatModel(R.drawable.ic_ben,"Ben", "You sent a photo."),
        ChatModel(R.drawable.ic_naomi,"Naomi", "Naomi sent a photo."),
        ChatModel(R.drawable.ic_alicia,"Alicia", "See you at 8."),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        adapter.updateItems(data)
        view.findViewById<EditText>(R.id.searchEditText).apply {
            background = null
            doAfterTextChanged {
                filterChats(it)
            }
        }


        adapter.setOnChatItemLongClickListener(object : ChatsAdapter.OnChatItemLongClickListener {
            override fun onItemLongClick(item: ChatModel) {

                AlertDialog.Builder(requireContext())
                    .setTitle("Сообщение")
                    .setMessage("Вы точно хотите удалить данный чат?")
                    . setPositiveButton("Да") { dialog, which ->

                        // Здесь мы открываем новый Алертдиалог в нутри первого
                        AlertDialog.Builder(requireContext())
                            .setTitle("Может все таки ")
                            .setMessage("не надо?")
                            .setNegativeButton("Ну ладно!!!") { dialog, which ->
                                dialog.dismiss()
                            }
                            . setPositiveButton("Я твердо решил!") { dialog, which ->
                                data.remove(item)
                                adapter.updateItems(data)
                            }
                            .create()
                            .show()

//                        data.remove(item)
//                        adapter.updateItems(data)
//                        Toast.makeText(requireContext(), "Chat deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Нет") { dialog, which ->

                        dialog.dismiss()
                    }
                    .create()
                    .show()

//                Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun filterChats(query: Editable?) {
        query?.let {
            if (it.isNotEmpty()) {
                val searchQuery = query.toString().lowercase()
                adapter.updateItems(data.filter {
                    it.title.lowercase().contains(searchQuery) ||
                            it.lastMessage.lowercase().contains(searchQuery)
                })
            } else adapter.updateItems(data)
        }
    }


}


