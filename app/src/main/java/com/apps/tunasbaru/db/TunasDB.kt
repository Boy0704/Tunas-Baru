package com.apps.tunasbaru.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apps.tunasbaru.model.DetailPendataan
import com.apps.tunasbaru.model.HeaderPendataan
import com.apps.tunasbaru.model.Karyawan

@Database(entities = [Karyawan::class, HeaderPendataan::class, DetailPendataan::class], version = 1, exportSchema = false)
abstract class TunasDB : RoomDatabase() {

    companion object{
        @Volatile
        private var INSTANCE: TunasDB? = null
        fun getDatabase(context: Context): TunasDB {
            return INSTANCE ?: synchronized(this) {
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    TunasDB::class.java,
                    "tunas_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getKaryawanDao(): KaryawanDao
    abstract fun getHPendataanDao(): HPendataanDao
    abstract fun getDPendataanDao(): DPendataanDao

}