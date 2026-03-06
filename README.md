# 🏗️ Arquitectura Hexagonal con Spring Boot

Proyecto demostrativo de **Arquitectura Hexagonal** (también conocida como "Ports & Adapters") implementado con Spring Boot y SQL Server.

## 📋 Tabla de Contenido

- [¿Qué es la Arquitectura Hexagonal?](#-qué-es-la-arquitectura-hexagonal)
- [🧁 Estructura del Proyecto](#-estructura-del-proyecto)
- [🧠 ¿Qué va en cada parte?](#-qué-va-en-cada-parte-explicado-fácil)
- [🔁 Flujo de Comunicación](#-flujo-de-comunicación)
- [💡 Beneficios](#-beneficios)
- [🚀 Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [⚙️ Configuración de Base de Datos](#️-configuración-de-base-de-datos)
- [🏃‍♂️ Ejecutar el Proyecto](#️-ejecutar-el-proyecto)

## 🏛️ ¿Qué es la Arquitectura Hexagonal?

La **Arquitectura Hexagonal** (también llamada **Ports & Adapters Architecture**) es un patrón de diseño creado por **Alistair Cockburn en 2005** que busca desacoplar completamente la aplicación de sus detalles técnicos externos.

### 🎯 Conceptos Clave (según Cockburn):

- **Hexágono Central**: Contiene toda la lógica de negocio pura de la aplicación
- **Ports (Puertos)**: Interfaces que definen contratos de comunicación
- **Adapters (Adaptadores)**: Implementaciones concretas que se conectan a los puertos
- **Inside/Outside**: El interior del hexágono nunca depende del exterior

### 📚 Origen Oficial:
- **Creador**: Dr. Alistair Cockburn
- **Año**: 2005 (publicado inicialmente en Portland Pattern Repository)
- **Nombre original**: "Ports and Adapters Architecture"
- **Nombre popular**: "Hexagonal Architecture" (por la representación visual de 6 caras potenciales)

## 📁 Estructura del Proyecto

```
src/main/java/com/kabq/arquitectura_hexagonal/
├── domain/
│   ├── model/          # 🏢 Entidades de negocio puras
│   └── port/
│       ├── in/         # 🔌 Casos de uso (interfaces)
│       └── out/        # 🔌 Repositorios (interfaces)
├── application/
│   └── service/        # ⚙️ Lógica de negocio (implementaciones)
└── infrastructure/
    ├── controller/     # 🎮 APIs REST
    ├── dto/            # 📦 Objetos de transferencia
    │   ├── request/
    │   └── response/
    └── persistence/    # 🗄️ Base de datos
        ├── entity/     # 📋 Tablas JPA
        ├── repository/ # 📚 JpaRepository
        └── adapter/    # 🔌 Adaptadores de repositorios
```

## 🎯 Diagrama Visual de Hexagonal Architecture

![Hexagonal Architecture Diagram](https://miro.medium.com/v2/resize:fit:1100/format:webp/1*yR4C1B-YfMh5zqpbHzTyag.png)

*Fuente: Diagrama oficial de Alistair Cockburn (creador de Hexagonal Architecture)*

Este diagrama muestra los conceptos clave:
- **Hexágono Central**: Contiene toda la lógica de negocio pura
- **Ports (Puertos)**: Interfaces que definen contratos de comunicación  
- **Adapters (Adaptadores)**: Implementaciones que se conectan a esos puertos

## 🚀 GraphQL Integración

En lugar de utilizar controladores REST tradicionales para todas las operaciones, este proyecto integra **GraphQL** usando `spring-boot-starter-graphql` para proporcionar una API más flexible y eficiente.

### Beneficios obtenidos con GraphQL:
1. **Evitar Over-fetching**: El cliente solo solicita los datos exactos del `Customer` o `Order` que necesita.
2. **Consultas a medida (Queries)**: Flexibilidad para obtener listas completas o buscar por ID con la estructura deseada.
3. **Operaciones de escritura (Mutations)**: Creación de clientes y órdenes manejadas a través de mutaciones tipadas fuertemente.

*Nota: Puedes probar la API interactiva en `http://localhost:8080/graphiql` una vez que levantes el proyecto.*

## 🧠 ¿QUÉ VA EN CADA PARTE? (explicado fácil)

### ❤️ domain = NEGOCIO

#### 📁 domain/model

Aquí van las entidades del negocio:
- `Customer.java`
- `Order.java`

**Ejemplo mental:**
- 👉 "qué es un cliente para mi negocio"
- 👉 "qué es un pedido"

**No llevan:**
- ❌ @Entity
- ❌ @RestController
- ❌ JPA
- ❌ Spring

**Solo:**
- ✔ atributos
- ✔ reglas de negocio

#### 📁 domain/port/in (puertos de entrada)

Aquí defines lo que el sistema puede hacer:
- `CreateCustomerUseCase.java`
- `CreateOrderUseCase.java`

**Ejemplo mental:**
- "mi sistema permite crear clientes"
- "mi sistema permite crear pedidos"

Son interfaces.

#### 📁 domain/port/out (puertos de salida)

Aquí defines lo que el sistema necesita del exterior:
- `CustomerRepositoryPort.java`
- `OrderRepositoryPort.java`

**Ejemplo mental:**
- "necesito guardar clientes"
- "necesito guardar pedidos"

También son interfaces.

### 🧠 application = CASOS DE USO (lógica)

#### 📁 application/service

Aquí van las implementaciones reales:
- `CreateCustomerService.java`
- `CreateOrderService.java`

**Ellos:**
- ✔ implementan puertos IN
- ✔ usan puertos OUT
- ✔ tienen lógica del negocio

**Ejemplo mental:**
- "para crear un pedido necesito un cliente válido"

### 🌍 infrastructure = MUNDO REAL

#### 🎮 controller

Aquí va:
- `CustomerController.java` (REST)
- `OrderController.java` (REST)
- `CustomerGraphqlController.java` (GraphQL)
- `OrderGraphqlController.java` (GraphQL)

**Ellos:**
- ✔ reciben HTTP (REST o GraphQL)
- ✔ usan DTO
- ✔ llaman casos de uso

Son: 👉 adaptadores de ENTRADA

#### 📦 dto

Aquí van los objetos que viajan por HTTP:
- `CreateCustomerRequest.java`
- `CreateOrderRequest.java`
- `OrderResponse.java`

**Sirven para:**
- 👉 no exponer directamente el dominio

#### 🗄️ persistence

Aquí vive la base de datos.

##### 📁 entity
- `CustomerEntity.java`
- `OrderEntity.java`

**Son:**
- ✔ JPA
- ✔ tablas
- ✔ relaciones

**Ejemplo:**
`OrderEntity` tiene `@ManyToOne` con `CustomerEntity`

##### 📁 repository
- `JpaCustomerRepository.java`
- `JpaOrderRepository.java`

**Estos extienden:**
`JpaRepository`

##### 📁 adapter
- `CustomerRepositoryAdapter.java`
- `OrderRepositoryAdapter.java`

**Estos:**
- ✔ implementan los puertos OUT
- ✔ traducen dominio ↔ entity
- ✔ conectan con JPA

Son: 👉 adaptadores de SALIDA

## 🔁 ¿CÓMO SE COMUNICAN?

**Flujo típico:**

```
HTTP Request
      ↓
Controller (adaptador entrada)
      ↓
UseCase (puerto IN)
      ↓
Service (caso de uso)
      ↓
RepositoryPort (puerto OUT)
      ↓
RepositoryAdapter (adaptador salida)
      ↓
JpaRepository
      ↓
DB
```

## 💥 ¿DÓNDE SE VE EL PODER?

Si mañana:
- **cambias MySQL por Mongo** 👉 solo cambias: `infrastructure/persistence`
- **cambias REST por Kafka (o completas la migración a GraphQL)** 👉 solo cambias: `infrastructure/controller`

**El dominio:**
- ✔ no cambia
- ✔ no se rompe
- ✔ sigue igual

## 💡 Beneficios de Hexagonal Architecture

1. **Desacoplamiento Completo**: La lógica de negocio nunca depende de frameworks externos
2. **Testabilidad Automatizada**: Posibilidad de ejecutar tests sin UI ni base de datos
3. **Flexibilidad Tecnológica**: Cambiar de SQL Server a MongoDB solo requiere cambiar adaptadores
4. **Mantenibilidad**: Código organizado según responsabilidade清晰
5. **Integración**: Facilita conectar múltiples aplicaciones sin intervención humana

### 🔄 Diferencia con Clean Architecture:

| Característica | Hexagonal Architecture | Clean Architecture |
|---------------|------------------------|-------------------|
| **Estructura** | Hexágono + Ports + Adapters | Capas concéntricas |
| **Enfoque** | Desacoplamiento de tecnologías | Reglas de dependencia |
| **Visualización** | Hexágono (6 caras) | Cebolla (anillos) |
| **Concepto clave** | Ports & Adapters | Dependency Rule |

> **Ambas arquitecturas comparten el mismo principio fundamental**: separar la lógica de negocio de los detalles técnicos, pero lo hacen de manera diferente.

## 🚀 Tecnologías Utilizadas

- **Java 21** - Lenguaje principal
- **Spring Boot 4.x** - Framework principal
- **Spring for GraphQL** - Implementación de API GraphQL
- **Spring Data JPA** - Persistencia
- **SQL Server** - Base de datos
- **Maven** - Gestión de dependencias
- **Lombok** - Reducción de código boilerplate
- **Docker** - Contenerización
- **Azure** - (ACR, ACA, SQL DB) para despliegue en la nube

## ⚙️ Configuración de Base de Datos

El proyecto ya está configurado para SQL Server. La configuración está en:

```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;DatabaseName=Cibertec;encrypt=false;trustServerCertificate=false;
spring.datasource.username=sa
spring.datasource.password=sql
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
```

### Requisitos previos:
1. SQL Server instalado y corriendo en localhost:1433
2. Base de datos `Cibertec` creada
3. Usuario `sa` con password `sql`

## 🏃‍♂️ Ejecutar el Proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Kevin-BQ/hexagonal-architecture.git
   cd hexagonal-architecture
   ```

2. **Ejecutar con Maven:**
   ```bash
   mvn spring-boot:run
   ```
   
   O con el wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Acceder a la aplicación:**
   - La aplicación correrá en `http://localhost:8080`
   - **GraphQL UI (GraphiQL):** Accede a `http://localhost:8080/graphiql` para probar las consultas.

## 📝 Estado Actual

---

**🎯 Recuerda:** Hexagonal Architecture separa **"qué hace"** tu sistema (dentro del hexágono) de **"cómo lo hace"** (adaptadores externos). ¡Tu negocio nunca debería depender de tecnologías específicas!

### 📚 Referencias Oficiales:
- **Alistair Cockburn - Hexagonal Architecture (2005)**: https://alistair.cockburn.us/hexagonal-architecture
- **AWS Prescriptive Guidance**: https://docs.aws.amazon.com/prescriptive-guidance/latest/cloud-design-patterns/hexagonal-architecture.html
- **Wikipedia**: https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)