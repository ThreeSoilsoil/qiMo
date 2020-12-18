package com.example.fragmentdemo.gamefragment

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cardgame.CardMatchingGame
//import com.example.fragmentdemo.R
import com.example.qimo.R


class CardRecyclerViewAdapter(val game: CardMatchingGame):RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardButton:Button
        init {
            cardButton = itemView.findViewById(R.id.cardButton)
        }
    }

    private var mOnclickListenLiser:((cardIndex:Int)->Unit)? = null
    fun setOnCardClickListener(l:(cardIndex:Int)->Unit) {
        mOnclickListenLiser = l

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return game.cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = game.cardAtIndex(position)
        holder.cardButton.isEnabled = !card.isMatched
        if (card.isMatched){
            holder.cardButton.text = ""
            holder.cardButton.setBackgroundResource(R.drawable.pai)
        }else{
            holder.cardButton.text = card.toString()
            holder.cardButton.setBackgroundColor(R.drawable.sicnu)
        }
        holder.cardButton.setOnClickListener {
            mOnclickListenLiser?.invoke(position)
        }
    }

}