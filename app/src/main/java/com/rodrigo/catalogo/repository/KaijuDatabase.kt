package com.rodrigo.catalogo.repository

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigo.catalogo.entity.KaijuEntity


@Database(entities = [KaijuEntity::class], version = 1)
abstract class KaijuDatabase: RoomDatabase() {

    abstract  fun kaijuDAO(): KaijuDAO

    companion object{
        private lateinit var instance: KaijuDatabase
        private const val DATABASE_NAME = "kaiju_db"

        fun getDatabase(context: Context): KaijuDatabase {
            if (!::instance.isInitialized){
                synchronized(this){
                    instance = androidx.room.Room.databaseBuilder(context, KaijuDatabase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }
}


