package com.hfad.qaydlar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [QaydlarData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class QaydlarDatabase : RoomDatabase() {

    abstract fun qaydlarDao(): QaydlarDao

    companion object {

        @Volatile
        private var INSTANCE: QaydlarDatabase? = null

        fun getDatabase(context: Context): QaydlarDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QaydlarDatabase::class.java,
                    "qaydlar_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}