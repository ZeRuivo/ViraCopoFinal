package com.example.vira_copo.model

import androidx.compose.runtime.mutableStateListOf

object Carrinho {
    // Lista que guarda os nomes dos pratos selecionados
    val itens = mutableStateListOf<String>()

    fun adicionar(nome: String) {
        itens.add(nome)
    }

    fun limpar() {
        itens.clear()
    }
}