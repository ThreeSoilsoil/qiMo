package cn.edu.sicnu.cardgame

import java.io.Serializable

class CardMatchingGame(val count: Int):Serializable {

    var score = 0
         set

    val cards: MutableList<Card>

    init {
        val deck = Deck()
        cards = mutableListOf()
        for (i in 1..count) {
            val card: Card? = deck.drawRandomCard()
            if (card != null) {
                cards.add(card)
            }
        }
    }
    fun reset() {
        val deck = Deck()
        cards.clear()
       // score = 0
        for (i in 1..count) {
            val card: Card? = deck.drawRandomCard()
            if (card != null) {
                cards.add(card)
            }
        }
    }

    fun cardAtIndex(index: Int): Card {
        return cards.get(index)
    }

    fun match(index: Int){
        cards[index].isMatched=true
    }

}