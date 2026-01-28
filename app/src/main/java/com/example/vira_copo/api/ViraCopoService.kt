package com.example.vira_copo.api

import com.example.vira_copo.model.ItemCardapio
import com.example.vira_copo.model.Pedido
import com.example.vira_copo.model.PedidoDto
import retrofit2.http.*

interface ViraCopoService {
    // Buscar listas
    @GET("/api/comidas")
    suspend fun getComidas(): List<ItemCardapio>

    @GET("/api/bebidas")
    suspend fun getBebidas(): List<ItemCardapio>

    // Pedidos
    @POST("/api/pedidos")
    suspend fun enviarPedido(@Body pedido: PedidoDto)

    @GET("/api/pedidos")
    suspend fun getPedidos(): List<Pedido>

    @DELETE("/api/pedidos/{id}")
    suspend fun apagarPedido(@Path("id") id: Int)

    // --- FUNÇÕES QUE FALTAVAM (E CORRIGEM O ERRO) ---

    // Adicionar (com @JvmSuppressWildcards para evitar erro de tipo)
    @POST("/api/comidas")
    suspend fun adicionarComida(@Body item: Map<String, @JvmSuppressWildcards Any>)

    @POST("/api/bebidas")
    suspend fun adicionarBebida(@Body item: Map<String, @JvmSuppressWildcards Any>)

    // Apagar
    @DELETE("/api/comidas/{id}")
    suspend fun apagarComida(@Path("id") id: Int)

    @DELETE("/api/bebidas/{id}")
    suspend fun apagarBebida(@Path("id") id: Int)
}