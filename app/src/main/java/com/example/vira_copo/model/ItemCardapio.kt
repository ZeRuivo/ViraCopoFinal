package com.example.vira_copo.model

data class ItemCardapio(
    val id: Int = 0,
    val nome: String,
    val disponivel: Boolean = true,
    val preco: Float = 0f,
    val descricao: String? = null,
    val categoria: String = "principais"
)

data class PedidoDto(
    val itens: List<String>,
    val numeroMesa: String
)