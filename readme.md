# 🚀 Accenture Technical Test - Franchise Management API

Bienvenido al repositorio de la solución para la prueba técnica Dev. Backend para Accenture. 
Esta es una **API Reactiva** construida bajo los principios de 
**Clean Architecture** y **Arquitectura Hexagonal**, diseñada para ser escalable, 
mantenible y de alto rendimiento.

## 🛠️ Tecnologías Utilizadas

* **Java 21** (LTS)
* **Spring Boot 3.5.13** (WebFlux - Programación Reactiva)
* **Spring Data R2DBC** (Acceso a datos no bloqueante)
* **PostgreSQL 16**

---

## 🏗️ Arquitectura del Proyecto

El proyecto implementa una estructura **Multi-módulo** siguiendo el patrón de Clean Architecture:

* **`domain`**: Contiene la lógica de negocio pura (Models, Use Cases y Puertos).
* **`infrastructure`**: Implementación de adaptadores (Persistence con R2DBC, Entry Points con WebFlux).
* **`application`**: Configuración central y el punto de entrada de la aplicación (`app-service`).

---

## ⚡ Levantar el Proyecto

He automatizado toda la infraestructura para que el entorno de pruebas esté listo en segundos. El sistema configurará automáticamente la base de datos con su esquema inicial.

**Requisitos:** Tener instalado Docker y Docker Compose.

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/Jaiderin123/accenture-skill-test.git
    ```

2.  **Ejecutar el contenedor:**
    ```bash
    cd accenture-skill-test
    sudo docker compose up --build -d
    ```

---

## 🧪 Pruebas con Postman

Para facilitar la evaluación de los endpoints, se he incluido una colección de Postman lista para usar con todos los flujos de la prueba técnica.

1.  **Localizar el archivo:** Dirígete a la raíz de este repositorio y busca el archivo `.json` de la colección (ej: `Accenture_Test.postman_collection.json`).
2.  **Importar en Postman:** Abre Postman y haz clic en el botón **"Import"** (ubicado en la parte superior izquierda).
3.  **Cargar colección:** Arrastra el archivo `.json` o selecciónalo desde tu explorador de archivos.
4.  **Explorar endpoints:** Una vez importada, tendrás a tu disposición carpetas organizadas para probar:
    * **Franquicias:** Creación y actualización de nombres como tambien obtener el reporte final de los producto con mayor stock por una franquicia determinada.
    * **Sucursales:** Agregación de nuevas sucursales a una franquicia.
    * **Productos:** CRUD completo, actualización de stock y eliminación.

---

## 👨‍💻 Autor
**Jaider Betancur Torres** - *Software Engineer*