package com.example.finalproject.roomdatabase

class KanjiRepository(private val dao:Dao) {
    val getkanji = dao.getkanji()

    suspend fun insert(kanji:KanjiEntity){
        return dao.insertkanji(kanji)
    }

    suspend fun delete(kanji:KanjiEntity){
        return dao.deletekanji(kanji)
    }

    suspend fun update(kanji: KanjiEntity){
        return dao.updatekanji(kanji)
    }


}