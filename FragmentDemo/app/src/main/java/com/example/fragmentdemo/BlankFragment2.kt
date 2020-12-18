package com.example.fragmentdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.lang.Exception
import java.lang.StringBuilder



const val CHAT_INTENT = "com.example.fragmentdemo.Chart.New"
const val ChatFile = "chatFile"

class BlankFragment2 : Fragment() {

    lateinit var msgList: MutableList<Msg>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val msgs = loadData()
        if (msgs !=null ) {
            msgList = msgs
        }else{
            msgList = mutableListOf()
        }
        val adapter = ChatAdapter(msgList)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val button_send = view.findViewById<Button>(R.id.button_send)
        val editChat = view.findViewById<EditText>(R.id.editChat)

        recyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        button_send.setOnClickListener {
            msgList.add(addMsg(editChat.text.toString()))
            adapter.notifyItemInserted(msgList.size-1)
            recyclerView.scrollToPosition(msgList.size-1)

            val intent = Intent(CHAT_INTENT)
            intent.setPackage(activity?.packageName)
            activity?.sendBroadcast(intent)

        }
    }

    fun addMsg(str:String): Msg {
        val randCount = (0..10).random()
        val builder = StringBuilder()
        for (i in 0..randCount){
            builder.append(str)
        }
        return Msg(builder.toString(),(0..1).random())
    }

    data class Msg(val content:String, val type:Int): Serializable {
        companion object {
            const val RECEIVE = 0
            const val SEND = 1
        }
    }

    class ChatAdapter(val mList:List<Msg>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
        class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val textView_leftMsg: TextView
            val textView_rightMsg: TextView
            val leftLayout: LinearLayout
            val rightLayou: LinearLayout
            init {
                textView_leftMsg = view.findViewById(R.id.leftMsg)
                textView_rightMsg = view.findViewById(R.id.rightMsg)
                leftLayout = view.findViewById(R.id.leftLayout)
                rightLayou = view.findViewById(R.id.rightLayout)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
            val msg = mList.get(position)
            if (msg.type == Msg.RECEIVE){
                holder.rightLayou.visibility = View.GONE
                holder.leftLayout.visibility = View.VISIBLE
                holder.textView_leftMsg.text = msg.content
            }else{
                holder.leftLayout.visibility = View.GONE
                holder.rightLayou.visibility = View.VISIBLE
                holder.textView_rightMsg.text = msg.content
            }
            holder.textView_rightMsg.setOnClickListener {

            }
            holder.textView_leftMsg.setOnClickListener {

            }
        }

        override fun getItemCount(): Int {
            return mList.size
        }

    }


    override fun onStop() {
        super.onStop()
        Log.d("BlankFragment1","onStop")
        saveData()

    }

    private fun loadData(): MutableList<Msg>? {
        try {
            val inputFile = activity?.openFileInput(ChatFile)
            val objectInputStream = ObjectInputStream(inputFile)

            val msgs = objectInputStream.readObject() as MutableList<Msg>

            objectInputStream.close()
            inputFile?.close()

            return msgs
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }

    }

    private fun saveData() {
        try {
            val outputFile = activity?.openFileOutput(ChatFile, Context.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(outputFile)

            objectOutputStream.writeObject(msgList)

            objectOutputStream.close()
            outputFile?.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}