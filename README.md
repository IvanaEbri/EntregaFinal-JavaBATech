# 💻 **Sistema de Gestión de Productos y Pedidos – Proyecto Integrador Back-End Java (BATech)**

Este proyecto implementa un **sistema de e-commerce** desarrollado en **Java**, utilizando **Spring Boot** y **MariaDB** para gestionar productos, pedidos, usuarios y categorías.
Incluye funcionalidades de catálogo, carrito, pedidos, stock y administración.

Es la **entrega final** del curso Back-End en Java y aplica los principales conceptos de:

* Programación Orientada a Objetos (POO)
* Arquitectura REST
* Capas Controller – Service – Repository
* Validaciones y manejo de excepciones
* Persistencia con JPA/Hibernate
* Modularización por paquetes
* Colecciones, opcionales, streams
* Manejo de errores y excepciones personalizadas

---

# 🚀 **Funcionalidades Principales**

## 🛒 **Gestión de Productos**

La API REST permite:

* Crear productos nuevos
* Listar todos los productos
* Buscar productos por ID o nombre
* Actualizar datos del producto (precio, stock, categoría, imagen…)
* Eliminar productos
* Validar que el stock no sea negativo
* Alertas cuando el stock llega a niveles críticos (opcional)

**Atributos del producto:**

* id
* nombre
* descripción
* precio
* categoría
* urlImagen
* stock

---

## 🗂️ **Gestión de Categorías**

* Crear y listar categorías
* Asignar categoría a un producto
* Filtrar productos por categoría

---

## 🛒 **Carrito de Compras**

* Agregar productos al carrito
* Actualizar cantidades
* Validación automática de stock
* Calcular total dinámicamente

---

## 📦 **Gestión de Pedidos**

* Crear pedido desde el carrito o seleccionando productos manualmente
* Lista de productos + cantidades
* Cálculo automático del total
* Confirmación del pedido
* Descuento automático de stock
* Manejo de estados del pedido:

    * pendiente
    * confirmado
    * enviado
    * entregado
    * cancelado 

---

## 📜 **Historial de Pedidos**

* Listar pedidos por ID de usuario
* Ver detalle del pedido
* Ver monto total y estado

---

## 👩‍💼 **Administración**

* Gestión completa de productos
* Gestión de stock
* Configuración técnica del sistema

---

# 🧠 **Conceptos Aplicados**

### ✔ Programación Orientada a Objetos

* Clases Product, PurchaseOrder, OrderLine, Client, Category
* Encapsulamiento y reutilización de código
* Relaciones uno-a-muchos y muchos-a-muchos

---

### ✔ Arquitectura REST con Spring Boot

Estructura por capas:

```
controller/
service/
repository/
model/
exception/
```
---

### ✔ Endpoints:

#### 📦 Orders (Pedidos):

##### 🔹 Pedido

* Crear un nuevo pedido: `POST /api/order`
* Obtener todos los pedidos: `GET /api/order`
* Obtener pedidos filtrados por cliente: `GET /api/order?client={clientId}`
* Eliminar un pedido por ID: `DELETE /api/order/{orderId}`

##### 🔹 Estados del pedido

* Confirmar un pedido: `PUT /api/order/{orderId}/confirm?client={clientId}`
* Marcar el pedido como enviado: `PUT /api/order/{orderId}/send`
* Marcar el pedido como entregado: `PUT /api/order/{orderId}/deliver`

##### 🔹 Líneas de pedido (OrderLine)

* Agregar una línea de pedido (libro + cantidad): `POST /api/order/line`
* Obtener las líneas asociadas a un pedido: `GET /api/order/{orderId}/lines`
* Eliminar una línea de pedido: `DELETE /api/order/line/{lineId}`

#### 📚 Books (Libros)

* Crear un nuevo libro: `POST /api/book`
* Obtener todos los libros:`GET /api/book`
* Obtener un libro por ID: `GET /api/book/{bookId}`
* Actualizar un libro existente: `PUT /api/book/{bookId}`
* Eliminar un libro: `DELETE /api/book/{bookId}`

##### 🔹 Búsquedas de libros

* Buscar libros por título, autor o categoría: `GET /api/book?search={texto}`
* Buscar libros por categoría: `GET /api/book?category={categoryId}`

#### 🗂️ Categories (Categorías)
* Crear una nueva categoría:`POST /api/category` 
* Buscar categoría por nombre:`GET /api/category?category={name}`
* Obtener todas las categorías: `GET /api/category`
* Actualizar una categoría: `PUT /api/category/{categoryId}`

#### 👤 Clients (Clientes)
* Crear un cliente: `POST /api/client`
* Obtener todos los clientes:`GET /api/client`
* Obtener un cliente por ID: `GET /api/client/{clientId}`
* Buscar cliente por nombre: `GET /api/client?clientName={name}`
* Actualizar datos de un cliente: `PUT /api/client/{clientId}`
* Eliminar un cliente: `DELETE /api/client/{clientId}`

---

### ✔ Excepciones

Incluye:

//TODO: revisar excepciones 
* `StockInsuficienteException`
* `ProductoNoEncontradoException`
* Manejo centralizado con `@ControllerAdvice`

---

### ✔ Persistencia con MariaDB y JPA

* Entities
* Repositorios `JpaRepository`
* Relaciones `@OneToMany`, `@ManyToOne`, `@ManyToMany`

---

# ⚙️ **Flujo de Uso del Sistema (Resumen)**

1️⃣ El usuario ingresa al sitio web
→ Frontend hace `GET /api/book`
→ Se muestran los productos

2️⃣ Agregar un producto
→ Formulario HTML
→ POST `/api/book` con JSON
→ El backend valída y guarda

3️⃣ Carrito
→ El usuario selecciona productos
→ Se valida stock
→ Se genera el total

4️⃣ Crear pedido
→ POST `/api/order`
→ Se descuenta stock
→ Pedido queda en estado "pendiente"

5️⃣ Listar pedidos
→ `GET /api/order?client={clientId}`
→ Se muestra historial y estados

---

# 🧩 **Estructura del Proyecto**

```
src/
 ├── main/
 │   ├── java/
 │   │   └── com.techlab.ecommerce/
 │   │        ├── controller/
 │   │        ├── service/
 │   │        ├── repository/
 │   │        ├── model/
 │   │        │   ├── dto/
 │   │        │   └──entity/
 │   │        └── exception/
 │   └── resources/
 │       └── application.properties
 └── test/
```

---

# 🗄️ **Base de Datos**

**La base de datos se encuentra corriendo en el puerto 3310**

Tablas principales:

* book
* category
* client
* purchase_order
* order_line

Incluye claves foráneas y relaciones entre entidades.

---

# 📚 **Tecnologías Utilizadas**

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* MariaDB
* Maven
* Insomnia para testeo




