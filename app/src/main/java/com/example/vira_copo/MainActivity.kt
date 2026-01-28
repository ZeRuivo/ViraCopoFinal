package com.example.vira_copo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vira_copo.Pages.*
import com.example.vira_copo.ui.theme.Vira_copoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vira_copoTheme {
                val navController = rememberNavController()

                // O NavHost gere todas as "estradas" da tua aplicação
                NavHost(navController = navController, startDestination = "Login") {
                    composable("Login") { LoginPage(navController) }

                    // Nomes das páginas principais
                    composable("HomePageEmp") { HomePageEmp(navController) }
                    composable("HomePageCoz") { HomePageCoz(navController) }
                    composable("HomePageGer") { HomePageGer(navController) }

                    // AQUI ESTAVA O ERRO: Mude de "comidas" para "ComidasPage"
                    composable("ComidasPage") { ComidasPage(navController) }

                    // E aqui de "bebidas" para "BebidasPage"
                    composable("BebidasPage") { BebidasPage(navController) }

                    // A rota do menu de gestão
                    composable("Gerenciar_cardapio") { GerenciarCardapioPage(navController) }
                }
                }
            }
        }
    }
