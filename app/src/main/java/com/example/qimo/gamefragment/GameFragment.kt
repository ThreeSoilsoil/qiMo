package com.example.fragmentdemo.gamefragment

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cardgame.Card
import cn.edu.sicnu.cardgame.Card.Companion.idiomImage
import cn.edu.sicnu.cardgame.CardMatchingGame
import com.example.qimo.R
import com.example.qimo.gamefragment.Timer
import kotlinx.android.synthetic.main.fragment_game.*

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception
import java.util.*

const val gameFile = "gameFile"
class GameFragment : Fragment() {

    companion object {
        private lateinit var game: CardMatchingGame
            var second = 0
            var running = false
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
        val reset = view.findViewById<Button>(R.id.button_changeidiom)

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

        var textView_count = 1   //清除四个textview的内容
        var textView_choose=1
        val card = Card
        var imageIndex=0

        val random = Random()
        var xuanze=""
        var match_count = arrayOfNulls<Int>(4)


        var once=0
        button_start.setOnClickListener {
            if(once==0){
                imageIndex=random.nextInt(idiomImage.size-1)
                imageView_idiom.setImageResource(idiomImage[imageIndex])
                once=1
                runTimer()
            }
            running=true

            button_pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24)
        }

        button_pause.setOnClickListener {
            running=false
            button_pause.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
        }
        imageView_idiom.setOnClickListener {
            second=0
            running=false
            button_pause.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
            imageIndex=random.nextInt(idiomImage.size)
            imageView_idiom.setImageResource(idiomImage[imageIndex])
        }

        val textView_one = view?.findViewById<TextView>(R.id.textView_one)
        val textView_two = view?.findViewById<TextView>(R.id.textView_two)
        val textView_three = view?.findViewById<TextView>(R.id.textView_three)
        val textView_four = view?.findViewById<TextView>(R.id.textView_four)
        adapter.setOnCardClickListener {
            Log.d("123456","${ game.cards[it].toString()}")
            //game.chooseCardAtIndex(it)
            match_count[textView_count-1]=it

            if(textView_choose==1) textView_one?.text=game.cards[it].toString()
            if(textView_choose==2) textView_two.text=game.cards[it].toString()
            if(textView_choose==3) textView_three.text=game.cards[it].toString()
            if(textView_choose==4) textView_four.text=game.cards[it].toString()

             xuanze = textView_one.text.toString()+textView_two.text.toString()+textView_three.text.toString()+textView_four.text.toString()


                    if(xuanze == card.correct[imageIndex]){
                        running=false
                        button_pause.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24)
                        xuanze=""
                        textView_one?.text=""
                        textView_two.text=""
                        textView_three.text=""
                        textView_four.text=""
                        Toast.makeText(this.context,"恭喜选对了",Toast.LENGTH_SHORT)
                            //.setGravity(Gravity.CENTER, 0, 0)
                            .show()
                        match_count[0]?.let { it1 -> game.match(it1) }
                        match_count[1]?.let { it1 -> game.match(it1) }
                        match_count[2]?.let { it1 -> game.match(it1) }
                        match_count[3]?.let { it1 -> game.match(it1) }
                        game.score +=4
                    }
            textView_count++
            if(textView_count==5) textView_count=1;

            updateUI()
        }
        textView_one.setOnClickListener {
            textView_one.setBackgroundResource(R.drawable.shapeblue)
            textView_two.setBackgroundResource(R.drawable.shape)
            textView_three.setBackgroundResource(R.drawable.shape)
            textView_four.setBackgroundResource(R.drawable.shape)
            textView_choose=1
        }
        textView_two.setOnClickListener {
            textView_two.setBackgroundResource(R.drawable.shapeblue)
            textView_one.setBackgroundResource(R.drawable.shape)
            textView_three.setBackgroundResource(R.drawable.shape)
            textView_four.setBackgroundResource(R.drawable.shape)

            textView_choose=2
        }
        textView_three.setOnClickListener {
            textView_three.setBackgroundResource(R.drawable.shapeblue)
            textView_one.setBackgroundResource(R.drawable.shape)
            textView_two.setBackgroundResource(R.drawable.shape)
            textView_four.setBackgroundResource(R.drawable.shape)
            textView_choose=3
        }
        textView_four.setOnClickListener {
            textView_four.setBackgroundResource(R.drawable.shapeblue)
            textView_one.setBackgroundResource(R.drawable.shape)
            textView_two.setBackgroundResource(R.drawable.shape)
            textView_three.setBackgroundResource(R.drawable.shape)
            textView_choose=4
        }
        button_changeidiom.setOnClickListener {
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
    fun runTimer(){
        val handler = Handler()
        val runnable = object : Runnable{
            override fun run() {
                val hours = second / 3600
                val minutes = (second % 3600) / 60
                val secs = second % 60
                textView_time.text = String.format("%02d:%02d:%02d", hours, minutes, secs)
                if (running) {
                    second++
                }
                handler.postDelayed(this, 2000)
            }
        }
        handler.post(runnable)
    }

}