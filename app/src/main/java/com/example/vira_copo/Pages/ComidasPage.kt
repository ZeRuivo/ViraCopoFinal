package com.example.vira_copo.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.vira_copo.model.ItemCardapio
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComidasPage(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var listaComidas by remember { mutableStateOf<List<ItemCardapio>>(emptyList()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                listaComidas = RetrofitClient.api.getComidas()
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao carregar comidas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Cabeçalho com botão de voltar
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
            Text("Escolher Comida", fontSize = 24.sp, color = Color(0xFFB07348))
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(listaComidas) { item ->
                Card(
                    onClick = {
                        // ADICIONA AO CARRINHO E MOSTRA MENSAGEM
                        Carrinho.adicionar(item.nome)
                        Toast.makeText(context, "${item.nome} adicionado!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(item.nome, fontSize = 18.sp, color = Color.Black)
                            Text(item.descricao ?: "", fontSize = 12.sp, color = Color.Gray)
                        }
                        Text("${item.preco}€", fontSize = 18.sp, color = Color(0xFF4CAF50))
                    }
                }
            }
        }
    }
}