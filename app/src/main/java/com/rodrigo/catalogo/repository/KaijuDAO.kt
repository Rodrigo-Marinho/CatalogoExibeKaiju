package com.rodrigo.catalogo.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rodrigo.catalogo.entity.KaijuEntity


@Dao
interface KaijuDAO {


    @Insert
    fun insert(kaiju: List<KaijuEntity>)

    @Query("SELECT * FROM Kaiju")
    fun getAllKaijus(): List<KaijuEntity>

    @Query("SELECT * FROM Kaiju WHERE id = :id")
    fun getKaijuById(id: Int): KaijuEntity

    @Update
    fun updateKaiju(kaiju: KaijuEntity)

    @Delete
    fun deleteKaiju(kaiju: KaijuEntity): Int

}