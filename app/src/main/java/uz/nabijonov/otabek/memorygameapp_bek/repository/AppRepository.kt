package uz.nabijonov.otabek.memorygameapp_bek.repository

import uz.nabijonov.otabek.memorygameapp_bek.R
import uz.nabijonov.otabek.memorygameapp_bek.data.common.CardData

class AppRepository {

    companion object {
        private lateinit var instance: AppRepository

        fun getInstance(): AppRepository {
            if (!(Companion::instance.isInitialized)) {
                instance = AppRepository()
            }
            return instance
        }
    }

    private val cardList = ArrayList<CardData>()

    init {
        cardList.add(CardData(R.drawable.image1, 1))
        cardList.add(CardData(R.drawable.image2, 2))
        cardList.add(CardData(R.drawable.image3, 3))
        cardList.add(CardData(R.drawable.image4, 4))
        cardList.add(CardData(R.drawable.image5, 5))
        cardList.add(CardData(R.drawable.image6, 6))
        cardList.add(CardData(R.drawable.image7, 7))
        cardList.add(CardData(R.drawable.image8, 8))
        cardList.add(CardData(R.drawable.image9, 9))
        cardList.add(CardData(R.drawable.image10, 10))
        cardList.add(CardData(R.drawable.image11, 11))
        cardList.add(CardData(R.drawable.image12, 12))
        cardList.add(CardData(R.drawable.image13, 13))
        cardList.add(CardData(R.drawable.image14, 14))
        cardList.add(CardData(R.drawable.image15, 15))
        cardList.add(CardData(R.drawable.image16, 16))
        cardList.add(CardData(R.drawable.image17, 17))
        cardList.add(CardData(R.drawable.image18, 18))
        cardList.add(CardData(R.drawable.image19, 19))
    }

    fun getData(count: Int): List<CardData> {
        cardList.shuffle()
        val list = cardList.subList(0, count / 2)
        val result = ArrayList<CardData>(count)
        result.addAll(list)
        result.addAll(list)
        result.shuffle()

        return result
    }
}