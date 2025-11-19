# Sistema de Autenticación en Spring Boot

Aplicación simple de autenticación desarrollada con Spring Boot, utilizando roles para controlar el acceso a distintas áreas del sistema.

## Descripción

Incluye:

- Inicio de sesión con Spring Security  
- Roles: **ADMIN** y **EMPLEADO**  
- Acceso restringido según rol  
- Vistas con Thymeleaf  
- Persistencia en MySQL  

## Tecnologías

- Java 17  
- Spring Boot 3  
- Spring Security  
- Spring Data JPA  
- Thymeleaf  
- MySQL  

## Requisitos

- JDK 17  
- Maven  
- MySQL  

## Instalación

1. Crear la base de datos `sistema_autenticacion`  
2. Configurar `application.properties`  
3. Ejecutar:


## Credenciales

- ADMIN → admin / Admin123!  
- EMPLEADO → user / User123!

## Rutas

- `/login`  
- `/panel`  
- `/admin/*`  
- `/empleado/*`

## Autor

Manuel Ibarra
