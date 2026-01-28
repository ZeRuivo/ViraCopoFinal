package com.example.vira_copo.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vira_copo.api.RetrofitClient
import com.example.vira_copo.model.ItemCardapio
import kotlinx.coroutines.launch
import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GerenciarCardapioPage(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    var listaItens by remember { mutableStateOf<List<ItemCardapio>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    // Campos do formulário
    var novoNome by remember { mutableStateOf("") }
    var novoPreco by remember { mutableStateOf("") }
    var novaCategoria by remember { mutableStateOf("entradas") }

    fun carregarDados() {
        coroutineScope.launch {
            try {
                val comidas = try { RetrofitClient.api.getComidas() } catch (e: Exception) { emptyList() }
                val bebidas = try { RetrofitClient.api.getBebidas() } catch (e: Exception) { emptyList() }
                listaItens = comidas + bebidas
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao carregar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) { carregarDados() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFFB07348)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            // Botão de Voltar ao Menu
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
            }

            PageHeader("Editar Pratos e Bebidas")

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(listaItens) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(item.nome, fontSize = 18.sp, color = Color.Black)
                                Text("${item.preco}€ - ${item.categoria}", color = Color.Gray)
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    try {
                                        val cats = listOf("bebidas", "refrigerante", "agua", "alcool")
                                        if (cats.contains(item.categoria.lowercase())) {
                                            RetrofitClient.api.apagarBebida(item.id)
                                        } else {
                                            RetrofitClient.api.apagarComida(item.id)
                                        }
                                        carregarDados()
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Erro ao apagar: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Apagar", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Novo Item") },
            text = {
                Column {
                    OutlinedTextField(value = novoNome, onValueChange = { novoNome = it }, label = { Text("Nome") })
                    OutlinedTextField(value = novoPreco, onValueChange = { novoPreco = it }, label = { Text("Preço (ex: 12.50)") })
                    OutlinedTextField(value = novaCategoria, onValueChange = { novaCategoria = it }, label = { Text("Categoria (entradas/prato/bebidas)") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    coroutineScope.launch {
                        try {
                            val precoFloat = novoPreco.replace(",", ".").toFloatOrNull() ?: 0f
                            val cat = novaCategoria.lowercase().trim()

                            val novoItemMap = mapOf(
                                "nome" to novoNome,
                                "preco" to precoFloat,
                                "categoria" to cat,
                                "descricao" to "Novo item",
                                "imagem" to ""
                            )

                            keyboardController?.hide() // Esconde teclado

                            val cats = listOf("bebidas", "refrigerante", "agua", "alcool")
                            if (cats.contains(cat)) {
                                RetrofitClient.api.adicionarBebida(novoItemMap)
                            } else {
                                RetrofitClient.api.adicionarComida(novoItemMap)
                            }

                            Toast.makeText(context, "Guardado com sucesso!", Toast.LENGTH_SHORT).show()
                            showDialog = false
                            novoNome = ""; novoPreco = ""
                            carregarDados()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }) { Text("Guardar") }
            },
            dismissButton = { Button(onClick = { showDialog = false }) { Text("Cancelar") } }
        )
    }
}