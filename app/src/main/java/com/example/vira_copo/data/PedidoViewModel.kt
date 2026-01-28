package com.example.vira_copo.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vira_copo.api.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import com.example.vira_copo.model.Pedido


class PedidoViewModel : ViewModel() {
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos = _pedidos.asStateFlow()

    init {
        // Começa a buscar pedidos automaticamente ao iniciar
        iniciarAtualizacaoAutomatica()
    }

    private fun iniciarAtualizacaoAutomatica() {
        viewModelScope.launch {
            while (isActive) { // Enquanto a app estiver aberta
                carregarPedidos()
                delay(5000) // Espera 5 segundos e busca de novo (Polling)
            }
        }
    }

    private suspend fun carregarPedidos() {
        try {
            val listaAtualizada = RetrofitClient.api.getPedidos()
            _pedidos.value = listaAtualizada
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun atualizarStatusPedido(id: Int, novoStatus: String) {
        // Nota: Para isto funcionar plenamente, precisarias de criar uma rota
        // na API para atualizar (PUT /api/pedidos/:id).
        // Por agora, vamos apenas simular visualmente na lista local:

        val listaAtual = _pedidos.value.toMutableList()
        val index = listaAtual.indexOfFirst { it.id == id }
        if (index != -1) {
            val pedidoAntigo = listaAtual[index]
            listaAtual[index] = pedidoAntigo.copy(status = novoStatus)
            _pedidos.value = listaAtual
        }
    }
    // Dentro da classe PedidoViewModel...

    fun apagarPedido(id: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.apagarPedido(id)
                // Atualiza a lista depois de apagar
                val listaAtualizada = RetrofitClient.api.getPedidos()
                _pedidos.value = listaAtualizada
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}