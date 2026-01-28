package com.example.vira_copo.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vira_copo.api.RetrofitClient
import com.example.vira_copo.model.ItemCardapio
import kotlinx.coroutines.launch

@Composable
fun HomePageGer(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var listaItens by remember { mutableStateOf<List<ItemCardapio>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    // Campos do formulário
    var novoNome by remember { mutableStateOf("") }
    var novoPreco by remember { mutableStateOf("") }
    var novaCategoria by remember { mutableStateOf("entradas") }

    fun carregarDados() {
        coroutineScope.launch {
            try {
                // Tenta carregar das duas listas separadas
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
            PageHeader("Gestão de Cardápio")

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(listaItens) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(item.nome, fontSize = 18.sp)
                                Text("${item.preco}€ - ${item.categoria}", color = Color.Gray)
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    try {
                                        // Verifica onde apagar
                                        if (item.categoria == "bebidas" || item.categoria == "refrigerante" || item.categoria == "agua" || item.categoria == "alcool") {
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

            Button(
                onClick = { navController.navigate("Login") { popUpTo("Login") { inclusive = true } } },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Sair")
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
                    OutlinedTextField(value = novoPreco, onValueChange = { novoPreco = it }, label = { Text("Preço") })
                    OutlinedTextField(value = novaCategoria, onValueChange = { novaCategoria = it }, label = { Text("Categoria (entradas/prato/bebidas)") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    coroutineScope.launch {
                        try {
                            val precoFloat = novoPreco.replace(",", ".").toFloatOrNull() ?: 0f
                            val cat = novaCategoria.lowercase().trim()

                            // Cria um mapa para enviar ao servidor (evita erro de ID duplicado)
                            val novoItemMap = mapOf(
                                "nome" to novoNome,
                                "preco" to precoFloat,
                                "categoria" to cat,
                                "descricao" to "Novo item adicionado",
                                "imagem" to ""
                            )

                            // Decide se guarda nas Bebidas ou Comidas
                            if (cat == "bebidas" || cat == "refrigerante" || cat == "agua" || cat == "alcool") {
                                RetrofitClient.api.adicionarBebida(novoItemMap)
                            } else {
                                RetrofitClient.api.adicionarComida(novoItemMap)
                            }

                            Toast.makeText(context, "Item guardado com sucesso!", Toast.LENGTH_SHORT).show()
                            carregarDados()
                            showDialog = false
                            novoNome = ""; novoPreco = ""
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