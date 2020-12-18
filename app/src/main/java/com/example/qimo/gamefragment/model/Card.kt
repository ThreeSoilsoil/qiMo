package cn.edu.sicnu.cardgame

import android.nfc.Tag
import android.util.Log
import com.example.qimo.R
import java.io.Serializable

class Card(private var rank: String,  var isChosen:Boolean = false, var isMatched:Boolean = false): Serializable  {
    companion object {
        val rankStrings= arrayOf("答", "大", "打", "我", "非", "你", "她", "所", "问", "无", "道", "知", "不","满","腹","经","纶","天","罗","地","网","东","山","再","起","一","枝","独",
            "秀","语","重","心","长","深","入","浅","出","自","以","为","是")
        val correct = arrayOf("满腹经纶","答非所问","无所不知","天罗地网","东山再起","一枝独秀","语重心长","深入浅出","自以为是")
        val idiomImage = arrayOf(R.drawable.manfujinglun,R.drawable.dafeisuowen, R.drawable.wusuobuzhi,R.drawable.tianluodiwang,
            R.drawable.dongshanzaiqi,R.drawable.yizhiduxiu,R.drawable.yuzhongxinchang,R.drawable.shenruqianchu,R.drawable.ziyiweishi)
    }

    override fun toString(): String {
        return  rank
    }


}