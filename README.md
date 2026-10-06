# clubmanager-mvc  (Trabajo 1: cliente-servidor + JPA + MySQL)
Cada entidad tiene 2 controladores:
- @Controller     (com.futbol.web)        -> vistas Thymeleaf
- @RestController (com.futbol.controller) -> API JSON en /api/...

## Ejecutar en local (JDK 17+)
1. Crea `local.properties` junto al pom.xml con MYSQL_ADDON_HOST, _PORT, _DB, _USER y _PASSWORD (no se sube a git).
2. `mvn spring-boot:run` y abre http://localhost:8080

## Desplegar en Render (Web Service, runtime Docker)
Variables de entorno: MYSQL_ADDON_HOST, MYSQL_ADDON_PORT, MYSQL_ADDON_DB, MYSQL_ADDON_USER, MYSQL_ADDON_PASSWORD
