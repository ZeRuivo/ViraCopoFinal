package com.example.vira_copo.model

data class Pedido(
    val id: Int,
    val itens: List<String>,
    val numeroMesa: String,
    val status: String,
    val hora: String? = null
)