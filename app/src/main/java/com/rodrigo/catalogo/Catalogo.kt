package com.rodrigo.catalogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.rodrigo.catalogo.entity.KaijuEntity
import com.rodrigo.catalogo.repository.KaijuRepository
import com.rodrigo.catalogo.ui.theme.CatalogoTheme


class Catalogo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = KaijuRepository.getInstance(this)
        repo.ensureInitialData()

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
    val context = LocalContext.current
    val repo = KaijuRepository.getInstance(context)

    var kaijus by remember { mutableStateOf(repo.getAllKaijus()) }
    var novoNome by remember { mutableStateOf("") }
    var novoDesc by remember { mutableStateOf("") }
    fun refresh() { kaijus = repo.getAllKaijus() }

    var popupNovoKaiju by remember { mutableStateOf(false) }

    var popupEditarKaiju by remember { mutableStateOf(false) }
    var editingKaiju by remember { mutableStateOf<KaijuEntity?>(null) }
    var editNome by remember { mutableStateOf("") }
    var editDesc by remember { mutableStateOf("") }

    Column(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { popupNovoKaiju = true }) {
                Text("Adicionar")
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(kaijus) { kaiju ->
                KaijuItem(
                    kaiju = kaiju,
                    onEdit = {
                        editingKaiju = it
                        editNome = it.nome
                        editDesc = it.descricao
                        popupEditarKaiju = true
                    },
                    onDelete = {
                        repo.deleteKaiju(it.id)
                        refresh()
                    }
                )
            }
        }
    }

    if (popupNovoKaiju) {
        AlertDialog(
            onDismissRequest = { popupNovoKaiju = false },
            title = { Text(text = "Novo Kaiju") },
            text = {
                Column {
                    OutlinedTextField(
                        value = novoNome,
                        onValueChange = { novoNome = it },
                        label = { Text("Nome") }
                    )
                    OutlinedTextField(
                        value = novoDesc,
                        onValueChange = { novoDesc = it },
                        label = { Text("Descrição") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (novoNome.isNotBlank() && novoDesc.isNotBlank()) {
                        val novoKaiju = KaijuEntity(id = 0, nome = novoNome, descricao = novoDesc, imagem = 0)
                        repo.insertKaiju(novoKaiju)
                        novoNome = ""
                        novoDesc = ""
                        popupNovoKaiju = false
                        refresh()
                    }
                }) { Text("Salvar") }
            },
            dismissButton = {
                Button(onClick = { popupNovoKaiju = false })
                { Text("Cancelar") }
            }
        )
    }

    if (popupEditarKaiju  == true) {
        AlertDialog(
            onDismissRequest = { popupEditarKaiju = false },
            title = { Text(text = "Editar Kaiju") },
            text = {
                Column {
                    OutlinedTextField(value = editNome, onValueChange = { editNome = it },
                        label = { Text("Nome") })
                    OutlinedTextField(value = editDesc, onValueChange = { editDesc = it },
                        label = { Text("Descrição") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    val old = editingKaiju ?: return@Button
                    val updated = KaijuEntity(id = old.id, nome = editNome, descricao = editDesc, imagem = old.imagem)
                    repo.updateKaiju(updated)
                    popupEditarKaiju = false
                    editingKaiju = null
                    val list = repo.getAllKaijus()
                    kaijus = list
                }) { Text("Salvar") }
            },
            dismissButton = {
                Button(onClick = { popupEditarKaiju = false }) { Text("Cancelar") }
            }
        )
    }
}


@Composable
fun KaijuItem(kaiju: KaijuEntity, onEdit: (KaijuEntity) -> Unit, onDelete: (KaijuEntity) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

            val resId = kaiju.imagem
            if (resId != 0) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = kaiju.nome,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(text = kaiju.nome, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(text = kaiju.descricao, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 6.dp))

                Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { onEdit(kaiju) }) { Text("Editar") }
                    Button(onClick = { onDelete(kaiju) }) { Text("Excluir") }
                }
            }
        }
    }
}


@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CatalogoScreenPreview() {
    CatalogoTheme {
        CatalogoScreen()
    }
}

