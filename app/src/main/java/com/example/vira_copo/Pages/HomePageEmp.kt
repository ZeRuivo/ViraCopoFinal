package com.example.vira_copo.Pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import com.example.vira_copo.model.Carrinho
import com.example.vira_copo.model.PedidoDto
import kotlinx.coroutines.launch

@Composable
fun HomePageEmp(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Variável para guardar o número da mesa
    var numeroMesa by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(title = "Novo Pedido")

        Column(modifier = Modifier.padding(16.dp).weight(1f)) {

            // 1. Campo para escrever a Mesa
            OutlinedTextField(
                value = numeroMesa,
                onValueChange = { numeroMesa = it },
                label = { Text("Número da Mesa") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Botões para ir ao Menu
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = { navController.navigate("ComidasPage") }, // Verifique se o nome na Rota é este ou "comidas"
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB07348))
                ) {
                    Text("Comidas")
                }
                Button(
                    onClick = { navController.navigate("BebidasPage") }, // Verifique se o nome na Rota é este ou "bebidas"
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB07348))
                ) {
                    Text("Bebidas")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Itens no Pedido:", fontSize = 18.sp, color = Color.Gray)

            // 3. Lista do Carrinho (O que vai ser enviado)
            Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color.White)) {
                if (Carrinho.itens.isEmpty()) {
                    Text(
                        "Nenhum item adicionado.",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.LightGray
                    )
                } else {
                    LazyColumn {
                        items(Carrinho.itens) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = item, fontSize = 16.sp)
                                    IconButton(onClick = { Carrinho.itens.remove(item) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remover", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Botão FINAL: ENVIAR PARA A COZINHA
            Button(
                onClick = {
                    if (numeroMesa.isBlank()) {
                        Toast.makeText(context, "Escreva o número da mesa!", Toast.LENGTH_SHORT).show()
                    } else if (Carrinho.itens.isEmpty()) {
                        Toast.makeText(context, "Adicione comidas ou bebidas!", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutineScope.launch {
                            try {
                                val pedido = PedidoDto(
                                    numeroMesa = numeroMesa,
                                    itens = Carrinho.itens.toList()
                                )
                                RetrofitClient.api.enviarPedido(pedido)

                                Toast.makeText(context, "Pedido enviado à Cozinha! 👨‍🍳", Toast.LENGTH_LONG).show()

                                // Limpar tudo para o próximo
                                Carrinho.limpar()
                                numeroMesa = ""

                            } catch (e: Exception) {
                                Toast.makeText(context, "Erro ao enviar: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(" CONFIRMAR PEDIDO", fontSize = 18.sp)
            }

            TextButton(
                onClick = { navController.navigate("Login") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Sair", color = Color.Gray)
            }
        }
    }
}