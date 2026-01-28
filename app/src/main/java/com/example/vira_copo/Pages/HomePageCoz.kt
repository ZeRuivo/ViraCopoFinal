package com.example.vira_copo.Pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vira_copo.data.PedidoViewModel
import kotlinx.coroutines.launch

@Composable
fun HomePageCoz(
    navController: NavController,
    viewModel: PedidoViewModel = viewModel()
) {
    val pedidos by viewModel.pedidos.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val backgroundColor = Color(0xFFF5F5DC)
    val textColor = Color.Black
    val headerColor = Color(0xFFB07348)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        // Coluna Principal que ocupa o ecrã todo
        Column(modifier = Modifier.fillMaxSize()) {

            // 1. Título (Topo)
            PageHeader(title = "Pedidos Confirmados")

            // 2. Lista de Pedidos (Meio - Ocupa o espaço que sobrar)
            Column(
                modifier = Modifier
                    .weight(1f) // <--- ISTO É FUNDAMENTAL: Empurra o botão de sair para baixo
                    .padding(16.dp)
            ) {
                if (pedidos.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nenhum pedido recebido", fontSize = 18.sp, color = textColor)
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(pedidos) { pedido ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Mesa: ${pedido.numeroMesa}", fontSize = 16.sp, color = textColor)
                                        Text(pedido.hora ?: "", fontSize = 14.sp, color = Color.Gray)
                                    }

                                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                                    pedido.itens.forEach { item ->
                                        Text("- $item", fontSize = 14.sp, color = textColor)
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "A preparar...",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )

                                        // Botão que APAGA o pedido
                                        Button(
                                            onClick = {
                                                coroutineScope.launch {
                                                    viewModel.apagarPedido(pedido.id)
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = headerColor)
                                        ) {
                                            Text("Pronto (Apagar)", color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 3. Botão de Voltar (Fundo - Fixo)
            Button(
                onClick = {
                    navController.navigate("Login") {
                        popUpTo("Login") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Sair / Voltar", color = Color.White)
            }
        }
    }
}