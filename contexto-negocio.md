# Contexto de la Prueba Técnica: Backend Developer - Accenture

## 1. Descripción del Proyecto
Se requiere construir una API para la gestión de franquicias comerciales. La jerarquía de datos es:
* **Franquicia:** Nombre (Único). Contiene sucursales.
* **Sucursal:** Nombre. Contiene productos.
* **Producto:** Nombre y Cantidad de Stock.

## 2. Requerimientos Funcionales (Endpoints)
* [ ] Agregar una nueva franquicia.
* [ ] Agregar una sucursal a una franquicia.
* [ ] Agregar un nuevo producto a una sucursal.
* [ ] Eliminar un producto de una sucursal.
* [ ] Modificar el stock de un producto.
* [ ] **Reporte Crítico:** Mostrar el producto con mayor stock por cada sucursal de una franquicia específica. (El resultado debe listar: Nombre de Sucursal, Nombre de Producto y Stock).

## 3. Puntos Extra (Diferenciadores Técnicos)
* [ ] Actualizar nombre de Franquicia.
* [ ] Actualizar nombre de Sucursal.
* [ ] Actualizar nombre de Producto.
* [ ] Infraestructura como Código (IaC): Terraform/CloudFormation.
* [ ] Despliegue en la Nube (AWS/Azure/GCP).

## 4. Criterios Técnicos de Evaluación (Mandatorios)
1. **Arquitectura:** Estructura basada en Clean Architecture.
2. **Programación:** Uso de Programación Reactiva (Spring WebFlux / R2DBC).
3. **Calidad:** Implementación de Unit Tests (Pruebas Unitarias).
4. **Contenerización:** Dockerización de la aplicación.
5. **Buenas Prácticas:** Legibilidad, mantenibilidad y principios SOLID.
6. **Persistencia:** Base de Datos Relacional o NoSQL (PostgreSQL con R2DBC preferiblemente).

## 5. Instrucciones de Entrega
* El proyecto debe subirse a un repositorio público (GitHub/BitBucket).
* Debe incluir un `README.md` detallado con instrucciones de despliegue local.
* Se evaluará el flujo de Git (Commit history).