package com.rodrigo.catalogo.repository

import android.content.Context
import com.rodrigo.catalogo.R
import com.rodrigo.catalogo.entity.KaijuEntity
import com.rodrigo.catalogo.repository.KaijuDatabase


class KaijuRepository private constructor(context: Context) {

    private var dataBase = KaijuDatabase.getDatabase(context).kaijuDAO()

    companion object {
        private fun getInitialKaijus(): List<KaijuEntity> {
            return listOf(
                KaijuEntity(id = 1, "Godzilla", "O Rei dos Monstros, uma criatura colossal que protege a Terra de monstros.",R.drawable.godzilla),
                KaijuEntity(2, "Kong", "O Rei dos Macacos, um gigante que habita a Ilha da Caveira.", R.drawable.kong),
                KaijuEntity(3, "King Ghidorah", "Um dragão de três cabeças e o principal antagonista dos mosntros.", R.drawable.king_ghidorah),
                KaijuEntity(4, "Mothra", "Uma gigantesca mariposa que é considerada uma deusa protetora.", R.drawable.mothra),
                KaijuEntity(5, "Rodan", "Um monstro voador que se assemelha a um pterodáctilo gigante.", R.drawable.rodan),
                KaijuEntity(6, "Mechagodzilla", "Uma versão robótica de Godzilla, criada para combater os monstros gigantes.", R.drawable.mechagodzilla),
                KaijuEntity(7, "Gigan", "Um monstro ciborgue com ganchos afiados e uma serra circular no peito.", R.drawable.gigan),
                KaijuEntity(8, "Destoroyah", "Um monstro mutante criado a partir dos restos do Oxygen Destroyer, o mesmo dispositivo que matou o Godzilla original.", R.drawable.destoroyah),
                KaijuEntity(9, "Biollante", "Um monstro híbrido criado a partir do DNA de Godzilla e uma rosa, resultando em uma criatura com características tanto de planta quanto de monstro.", R.drawable.biollante))

        }

        private lateinit var instance: KaijuRepository

        fun getInstance(context: Context): KaijuRepository {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = KaijuRepository(context)
                }
            }
            return instance
        }
    }


    fun loadInitialData() {
        val kaiju = getInitialKaijus()
        dataBase.insert(kaiju)
    }

    fun ensureInitialData() {
        if (getAllKaijus().isEmpty()) {
            loadInitialData()
        }
    }

    fun getAllKaijus(): List<KaijuEntity>{
        return dataBase.getAllKaijus()
    }


    fun getKaijuByID(id: Int): KaijuEntity{
        return dataBase.getKaijuById(id)
    }


    fun deleteKaiju(id: Int): Boolean {
        return dataBase.deleteKaiju(getKaijuByID(id)) > 0
    }

    fun updateKaiju(kaiju: KaijuEntity) {
        dataBase.updateKaiju(kaiju)
    }

    fun insertKaiju(kaiju: KaijuEntity) {
        dataBase.insert(listOf(kaiju))
    }
}
