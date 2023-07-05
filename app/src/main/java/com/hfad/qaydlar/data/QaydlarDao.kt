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
}