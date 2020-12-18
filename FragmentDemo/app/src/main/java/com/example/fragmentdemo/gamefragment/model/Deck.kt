package cn.edu.sicnu.cardgame

import java.util.Random

class Deck() {
    private val cards = mutableListOf<Card>() //构造牌
    private val r = Random()
    init {
        for (rank in Card.rankStrings) {
            //for (rank in Card.rankStrings) {
                val card = Card( rank=rank)
                cards.add(card)
            //}
        }
    }

    fun drawRandomCard(): Card? {           //取牌
        var randomCard: Card? = null
        if (cards.size > 0) {
            randomCard = cards.removeAt(r.nextInt(cards.size))
        }
        return randomCard
    }
}