package com.example.vira_copo.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vira_copo.model.ItemCardapio

// --- ESTE É O CABEÇALHO QUE FALTAVA ---
@Composable
fun PageHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFB07348)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

// O botão que já lá estava
@Composable
fun ItemBotao(item: ItemCardapio, selectedList: MutableList<String>) {
    val isSelected = selectedList.contains(item.nome)
    Button(
        onClick = {
            if (isSelected) selectedList.remove(item.nome) else selectedList.add(item.nome)
        },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Gray else Color(0xFFB07348)
        )
    ) {
        Text("${item.nome} - ${item.preco}€", color = Color.White)
    }
}