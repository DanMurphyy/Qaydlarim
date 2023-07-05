package com.hfad.qaydlar.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QaydlarDao {

    @Query("SELECT * FROM qaydlar_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<QaydlarData>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(qaydlarData: QaydlarData)

    @Update
    fun updateData(qaydlarData: QaydlarData)

    @Delete
    fun deleteItem(qaydlarData: QaydlarData)

    @Query("DELETE FROM qaydlar_table")
    fun deleteAll()

    @Query("SELECT * FROM qaydlar_table WHERE title LIKE :searchQuery OR description LIKE:searchQuery ")
    fun searchDatabase(searchQuery: String): LiveData<List<QaydlarData>>

    @Query("SELECT * FROM qaydlar_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END ")
    fun sortByHighPriority(): LiveData<List<QaydlarData>>

    @Query("SELECT * FROM qaydlar_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END ")
    fun sortByLowPriority(): LiveData<List<QaydlarData>>
}