package com.hfad.qaydlar.data

import androidx.lifecycle.LiveData

class QaydlarRepository(private val qaydlarDao: QaydlarDao) {

    val getAllData: LiveData<List<QaydlarData>> = qaydlarDao.getAllData()
    val sortByHighPriority: LiveData<List<QaydlarData>> = qaydlarDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<QaydlarData>> = qaydlarDao.sortByLowPriority()

    suspend fun insertData(qaydlarData: QaydlarData) {
        qaydlarDao.insertData(qaydlarData)
    }

    suspend fun updateData(qaydlarData: QaydlarData) {
        qaydlarDao.updateData(qaydlarData)
    }

    suspend fun deleteItem(qaydlarData: QaydlarData) {
        qaydlarDao.deleteItem(qaydlarData)
    }

    suspend fun deleteAll() {
        qaydlarDao.deleteAll()
    }

     fun searchDatabase(searchQuery: String): LiveData<List<QaydlarData>> {
        return qaydlarDao.searchDatabase(searchQuery)
    }
}