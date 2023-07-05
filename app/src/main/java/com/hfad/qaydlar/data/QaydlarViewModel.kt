package com.hfad.qaydlar.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QaydlarViewModel(application: Application) : AndroidViewModel(application) {

    private val qaydlarDao = QaydlarDatabase.getDatabase(application).qaydlarDao()
    private val repository: QaydlarRepository

    val getAllData: LiveData<List<QaydlarData>>
    val sortByHighPriority: LiveData<List<QaydlarData>>
    val sortByLowPriority: LiveData<List<QaydlarData>>

    init {
        repository = QaydlarRepository(qaydlarDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(qaydlarData: QaydlarData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(qaydlarData)
        }
    }

    fun updateData(qaydlarData: QaydlarData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(qaydlarData)
        }
    }

    fun deleteItem(qaydlarData: QaydlarData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(qaydlarData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<QaydlarData>> {
        return repository.searchDatabase(searchQuery)
    }
}