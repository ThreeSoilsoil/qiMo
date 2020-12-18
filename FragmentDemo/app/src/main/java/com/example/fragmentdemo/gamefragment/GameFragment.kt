package com.example.fragmentdemo.gamefragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cardgame.Card
import cn.edu.sicnu.cardgame.Card.Companion.correct
import cn.edu.sicnu.cardgame.CardMatchingGame
import com.example.fragmentdemo.R

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception
import java.sql.Array

const val gameFile = "gameFile"
class GameFragment : Fragment() {

    companion object {
        private lateinit var game: CardMatchingGame
    }
    val cardButtons = mutableListOf<Button>()
    lateinit var adapter:CardRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recylerView = view.findViewById<RecyclerView>(R.id.recylerView1)
        val reset = view.findViewById<Button>(R.id.reset)

        val rgame = loadData()
        if (rgame != null) {
            game = rgame
        }else{
            game = CardMatchingGame(24)
        }
        adapter = CardRecyclerViewAdapter(game)
        recylerView.adapter = adapter

        val configuration = resources.configuration
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recylerView.layoutManager = GridLayoutManager(activity, 8)
        }else{
            val gridLayoutManager = GridLayoutManager(activity, 6)
            recylerView.layoutManager = gridLayoutManager
        }

        updateUI()
        var textView_count = 1
        val card = Card
        //card.
        var xuanze=""
        var match_count = arrayOfNulls<Int>(4)

        adapter.setOnCardClickListener {
            Log.d("123456","${ game.cards[it].toString()}")
            //game.chooseCardAtIndex(it)
            match_count[textView_count-1]=it
            val textView_one = view?.findViewById<TextView>(R.id.textView_one)
            val textView_two = view?.findViewById<TextView>(R.id.textView_two)
            val textView_three = view?.findViewById<TextView>(R.id.textView_three)
            val textView_four = view?.findViewById<TextView>(R.id.textView_four)
            if(textView_count==1) textView_one?.text=game.cards[it].toString()
            if(textView_count==2) textView_two.text=game.cards[it].toString()
            if(textView_count==3) textView_three.text=game.cards[it].toString()
            if(textView_count==4) textView_four.text=game.cards[it].toString()
             xuanze = textView_one.text.toString()+textView_two.text.toString()+textView_three.text.toString()+textView_four.text.toString()
            if(textView_count==4){
                textView_one?.text=""
                textView_two.text=""
                textView_three.text=""
                textView_four.text=""
                for(i in 0..card.correct.size-1){
                    if(xuanze == card.correct[i]){
                        xuanze=""
                        match_count[0]?.let { it1 -> game.match(it1) }
                        match_count[1]?.let { it1 -> game.match(it1) }
                        match_count[2]?.let { it1 -> game.match(it1) }
                        match_count[3]?.let { it1 -> game.match(it1) }
                        game.score +=4
                    }
                }

            }
            textView_count++
            if(textView_count==5) textView_count=1;

            updateUI()
        }

        reset.setOnClickListener {
            game.reset()
            updateUI()
        }
    }


    fun updateUI() {
        adapter.notifyDataSetChanged()
        val score = view?.findViewById<TextView>(R.id.score)
        score?.text = getString(R.string.score) + game.score
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    fun saveData() {
        try {
            val output = activity?.openFileOutput(gameFile, AppCompatActivity.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(output)
            objectOutputStream.writeObject(game)
            objectOutputStream.close()
            output?.close()
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun loadData(): CardMatchingGame? {
        try {
            val input = activity?.openFileInput(gameFile)
            val objectInputStream =  ObjectInputStream(input)
            val game = objectInputStream.readObject() as CardMatchingGame
            objectInputStream.close()
            input?.close()
            return game
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}