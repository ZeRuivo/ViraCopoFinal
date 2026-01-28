package com.example.vira_copo.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vira_copo.R

@Composable
fun LoginPage(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F5DC) // Fundo Bege
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logotipo
            Image(
                painter = painterResource(id = R.drawable.logovc),
                contentDescription = "Logotipo",
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = androidx.compose.ui.layout.ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("Quem está a usar?", fontSize = 24.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(32.dp))

            // --- BOTÃO EMPREGADO ---
            Button(
                onClick = { navController.navigate("HomePageEmp") }, // Vai direto para Empregados
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB07348))
            ) {
                Text("Sou Empregado", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- BOTÃO COZINHEIRO ---
            Button(
                onClick = { navController.navigate("HomePageCoz") }, // Vai direto para Cozinha
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde
            ) {
                Text("Sou Cozinheiro", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- BOTÃO GERENTE ---
            Button(
                onClick = { navController.navigate("HomePageGer") }, // Vai direto para Gerente
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Azul
            ) {
                Text("Sou Gerente", fontSize = 18.sp)
            }
        }
    }
}