# Walkthrough: Resolución del TP

Este documento detalla todas las implementaciones realizadas para dar cumplimiento al alcance mínimo del Trabajo Práctico de Microservicios.

## 1. Creación de `order-service`
Se creó un nuevo módulo `order-service` siguiendo el estándar de Arquitectura Hexagonal.
- **Domain**: Entidad `Order` e interfaces `OrderUseCase`, `OrderRepositoryPort` y `EventPublisherPort`.
- **Application**: Lógica en `OrderService`.
- **Infrastructure**:
  - `OrderController`: Endpoint `POST /api/orders` protegido por JWT.
  - `OrderPersistenceAdapter`: Guardado en base de datos en memoria (H2).
  - `SecurityConfig`: Configuración de Resource Server de Spring Security.

## 2. Integración al Ecosistema
El servicio quedó correctamente configurado dentro de la red del Gateway y de registro:
- Añadido el módulo al **pom.xml** padre.
- Agregado como cliente **Eureka** en sus application properties (`prefer-ip-address: true`, defaultZone apuntando a Eureka Server).
- Ruteo incorporado en **API Gateway**: todo llamado a `/api/orders/**` se redirige a `order-service` mediante `lb://order-service`.
- Añadido a **Zipkin/Micrometer** con la configuración adecuada de traces (log format y export de zipkin api).

## 3. Integración con RabbitMQ (Mensajería)
El sistema ahora soporta el flujo completo de eventos para los servicios relacionados:
- **Order Service consume**: `ProductCreatedEvent` enviado a través de `inventory.exchange`. (Configuramos el listener `ProductEventListener`).
- **Order Service publica**: Al momento de crear una orden, emite un `OrderCreatedEvent` a través de RabbitMQ hacia `ecosystem-exchange`.
- **Notification Service consume**: Este servicio se actualizó creando un nuevo Listener que reacciona a las notificaciones emitidas por las órdenes y las loggea en consola.

## 4. Docker Compose
Se crearon las definiciones `order-service-rabbitmq` y `order-service-kafka` en el archivo principal de **docker-compose.yml**.
Además, se modificaron los `Dockerfile` de todos los módulos para contemplar la compilación e inyección del nuevo microservicio.

> [!TIP]
> Para probar todo el ecosistema y ver el comportamiento en vivo, puedes ejecutar:
> ```bash
> docker-compose --profile rabbitmq up -d --build
> ```
> Una vez arriba, utiliza Postman (o el archivo `.http` provisto) para pedir un JWT, crea un producto (lo cual gatillará un evento que será consumido en order-service) y luego crea una Orden (lo cual enviará un evento que atrapará notification-service). Podrás ver en **Zipkin** (puerto 9411) toda la traza de la petición.



CREAR ORDEN

Invoke-RestMethod -Method Post -Uri "http://localhost:8085/api/orders" -Headers @{ "Authorization" = "Bearer TU_TOKEN_AQUÍ"; "Content-Type" = "application/json" } -Body '{"productId":"P123","quantity":1}'
