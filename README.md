# 💻 **Sistema de Gestión de Productos y Pedidos – Proyecto Integrador Back-End Java (BATech)**

Este proyecto implementa un **sistema de e-commerce** desarrollado en **Java**, utilizando **Spring Boot** y **MariaDB** para gestionar productos, pedidos, usuarios y categorías.
Incluye funcionalidades de catálogo, carrito, pedidos, stock y administración, integrándose con una aplicación de Front-End (HTML/JS).

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

### Ver si se suma
* Manejo de estados del pedido:

    * pendiente
    * confirmado
    * enviado
    * entregado
    * cancelado 

---
### Ver si se suma
## 📜 **Historial de Pedidos**

* Listar pedidos por ID de usuario
* Ver detalle del pedido
* Ver monto total, fecha y estado

---

## 👩‍💼 **Administración**

* Gestión completa de productos
* Gestión de stock
### Ver si se suma
* Gestión de usuarios (opcional)
* Configuración técnica del sistema

---

# 🧠 **Conceptos Aplicados**

### ✔ Programación Orientada a Objetos

* Clases Producto, Pedido, LineaPedido, Usuario, Categoría
* Encapsulamiento y reutilización de código
* Relaciones uno-a-muchos y muchos-a-muchos
* Polimorfismo opcional para productos especializados (Bebida, Tecnología, etc.)

---

### ✔ Colecciones

* `List<Producto>`
* `List<LineaPedido>`
* `Map<Integer,Integer>` para relacionar producto-cantidad (opcional)

---

### ✔ Arquitectura REST con Spring Boot

Estructura por capas:

```
controller/
service/
repository/
model/
dto/
exception/
```

Endpoints principales:

* `GET /api/productos`
* `POST /api/productos`
* `PUT /api/productos/{id}`
* `DELETE /api/productos/{id}`
* `POST /api/pedidos`
* `GET /api/usuarios/{id}/pedidos`

---

### ✔ Excepciones

Incluye:

* `StockInsuficienteException`
* `ProductoNoEncontradoException`
* Manejo centralizado con `@ControllerAdvice`

---

### ✔ Persistencia con MariaDB y JPA

* Entities
* Repositorios `JpaRepository`
* Relaciones @OneToMany, @ManyToOne, @ManyToMany
* Validaciones con `@NotNull`, `@Min`, etc.

---

# ⚙️ **Flujo de Uso del Sistema (Resumen)**

1️⃣ El usuario ingresa al sitio web
→ Frontend hace `GET /api/productos`
→ Se muestran los productos

2️⃣ Agregar un producto
→ Formulario HTML
→ POST `/api/productos` con JSON
→ El backend valida y guarda

3️⃣ Carrito
→ El usuario selecciona productos
→ Se valida stock
→ Se genera el total

4️⃣ Crear pedido
→ POST `/api/pedidos`
→ Se descuenta stock
→ Pedido queda en estado "pendiente"

5️⃣ Listar pedidos
→ `GET /api/usuarios/{id}/pedidos`
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
 │   │        ├── dto/
 │   │        └── exception/
 │   └── resources/
 │       ├── application.properties
 │       └── schema.sql (opcional)
 └── test/
```

---

# 🗄️ **Base de Datos**

Tablas principales:

* productos
* categorias
* usuarios
* pedidos
* lineas_pedido

Incluye claves foráneas y relaciones entre entidades.

---

# 📚 **Tecnologías Utilizadas**

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* MariaDB
* Maven
* Lombok
* Postman para testeo
### Ver si se suma
* (Opcional) Spring Security para autenticación




