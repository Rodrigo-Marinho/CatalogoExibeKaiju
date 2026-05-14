package com.rodrigo.catalogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigo.catalogo.ui.theme.CatalogoTheme


data class Monstro(
    val id: Int,
    val nome: String,
    val descricao: String,
    val imageRes: Int? = null
)

class Catalogo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatalogoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        CatalogoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CatalogoScreen(modifier: Modifier = Modifier) {
    val monstros = listOf(
        Monstro(1, "Godzilla", "O Rei dos Monstros, uma criatura colossal que protege a Terra de monstros.", R.drawable.godzilla),
        Monstro(2, "Kong", "O Rei dos Macacos, um gigante que habita a Ilha da Caveira.", R.drawable.kong),
        Monstro(3, "King Ghidorah", "Um dragão de três cabeças e o principal antagonista dos mosntros.", R.drawable.king_ghidorah),
        Monstro(4, "Mothra", "Uma gigantesca mariposa que é considerada uma deusa protetora.", R.drawable.mothra),
        Monstro(5, "Rodan", "Um monstro voador que se assemelha a um pterodáctilo gigante.", R.drawable.rodan),
        Monstro(6, "Mechagodzilla", "Uma versão robótica de Godzilla, criada para combater os monstros gigantes.", R.drawable.mechagodzilla),
        Monstro(7, "Gigan", "Um monstro ciborgue com ganchos afiados e uma serra circular no peito.", R.drawable.gigan),
        Monstro(8, "Destoroyah", "Um monstro mutante criado a partir dos restos do Oxygen Destroyer, o mesmo dispositivo que matou o Godzilla original.", R.drawable.destoroyah),
        Monstro(9, "Biollante", "Um monstro híbrido criado a partir do DNA de Godzilla e uma rosa, resultando em uma criatura com características tanto de planta quanto de monstro.", R.drawable.biollante)
    )


    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {


        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            monstros.forEach { monstro ->
                MonstroCard(monstro = monstro)
            }
        }
    }
}

@Composable
fun MonstroCard(monstro: Monstro) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

            val resId = monstro.imageRes ?: 0

            if (resId != 0) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = monstro.nome,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Imagem", fontSize = 12.sp)
                }
            }

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = monstro.nome,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = monstro.descricao,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoScreenPreview() {
    CatalogoTheme {
        CatalogoScreen()
    }
}

