# Decisiones de Arquitectura - Catalog Service

## 1. Modelo de Concurrencia (Fecha: Mayo 2026)
- **Decisión:** Habilitar Java 21 Virtual Threads (`spring.threads.virtual.enabled=true`).
- **Contexto:** Al ser un microservicio que realizará múltiples llamadas I/O (Base de datos, otros servicios), necesitamos alta concurrencia.
- **Consecuencia:** Mayor throughput sin necesidad de utilizar programación reactiva (WebFlux).