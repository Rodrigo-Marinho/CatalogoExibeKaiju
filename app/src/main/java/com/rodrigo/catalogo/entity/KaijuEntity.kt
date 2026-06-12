package com.rodrigo.catalogo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Kaiju")
data class KaijuEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name="nome")
    val nome: String,

    @ColumnInfo(name="descricao")
    val descricao: String,

    @ColumnInfo(name="imagem")
    val imagem: Int
)


