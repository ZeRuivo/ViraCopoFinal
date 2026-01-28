# 🍽️ Vira Copo - Sistema de Gestão de Restaurante

Este projeto foi desenvolvido no âmbito da Unidade Curricular de **Programação de Aplicação Servidor**. O objetivo foi criar uma solução integrada para restaurantes, composta por uma **Aplicação Android** (para gestão de pedidos e cardápio) e uma **Aplicação Web** (para a cozinha e API).

## 📂 Estrutura do Repositório

O projeto encontra-se organizado em duas pastas principais, conforme solicitado:

* **`AppMov/`**: Contém o código-fonte da aplicação Android (Kotlin/Jetpack Compose).
* **`AppWeb/`**: Contém o código-fonte do servidor, API e Dashboard Web (Laravel).

---

## 🚀 Funcionalidades

### 📱 Aplicação Móvel (Android)
A aplicação móvel serve como terminal para os funcionários e gestores.

* **Autenticação Simplificada:** Login rápido via botões para perfis distintos (Empregado, Cozinheiro, Gerente).
* **Perfil Empregado:**
    * Seleção de mesa.
    * Visualização do Cardápio (Comidas e Bebidas vindas da API).
    * **Carrinho de Compras:** Adicionar itens localmente antes de enviar.
    * Envio do pedido completo para a Cozinha (API).
* **Perfil Gerente:**
    * **Gestão de Cardápio (CRUD):** Adicionar novos pratos/bebidas com preço e categoria.
    * Remover itens do cardápio.
* **Tecnologias:** Kotlin, Jetpack Compose, Retrofit (Consumo de API REST).

### 💻 Aplicação Web (Back-end & Cozinha)
O servidor central que gere os dados e a comunicação.

* **API REST:** Fornece os dados (JSON) para a aplicação móvel (Listar itens, Receber pedidos).
* **Dashboard da Cozinha:**
    * Interface Web para visualização dos pedidos em tempo real.
    * Listagem detalhada dos itens por pedido e número da mesa.
    * Tratamento automático de dados (Casts de JSON para Array).
* **Base de Dados:** Gestão de persistência de dados (Pratos e Pedidos).
* **Tecnologias:** PHP, Laravel Framework, Blade Templates, MySQL/SQLite.

---

## 🛠️ Instalação e Execução

Para testar o projeto, siga os passos abaixo para cada componente.

### 1. Configurar o Servidor (AppWeb)

Requisitos: PHP e Composer instalados.

```bash
cd AppWeb
# Instalar dependências
composer install

# Configurar a base de dados (cria o ficheiro .env se não existir)
cp .env.example .env
php artisan key:generate

# Criar tabelas e inserir dados de teste (Menu inicial)
php artisan migrate:fresh --seed

# Iniciar o servidor
php artisan serve
