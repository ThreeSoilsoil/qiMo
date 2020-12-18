package com.example.fragmentdemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment

import java.util.*


class BlankFragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("BlankFragment1", "onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonimage2 = view.findViewById<Button>(R.id.buttonimage2)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        val random = Random()
        val idiomImage = arrayOf(
            R.drawable.sicnu,
            R.drawable.dafeisuowen,
            R.drawable.manfujinglun,
            R.drawable.wusuobuzhi
        )                     //成语图片数组
        buttonimage2.setOnClickListener {
            val l=R.drawable.sicnu
            Toast.makeText(this.context,"12345",Toast.LENGTH_SHORT).show()
            //randomCard = cards.removeAt(r.nextInt(cards.size))
            val index=random.nextInt(idiomImage.size - 1)
            imageView.setImageResource(idiomImage[index])
            Log.d("123456", "${index}")

        }
    }


}