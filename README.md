# Práctica: Diseño y Pruebas Unitarias

Este repositorio contiene la implementación y las pruebas unitarias del caso de uso **"Supervisar tratamiento"** para el *Sistema Integral de Historia Clínica Electrónica*.

El proyecto ha sido desarrollado como parte de la asignatura **Ingeniería de Software (Curso 2025/2026)**.

## 📋 Descripción del Proyecto

El objetivo principal es aplicar técnicas de **Diseño de Software** (Patrones GRASP, principios SOLID, Value Objects) y **Pruebas Unitarias** exhaustivas utilizando dobles de prueba (Mocks).

El sistema simula la interacción de un médico con un terminal de consulta, permitiendo:
*   Consultar la historia clínica y prescripciones de un paciente.
*   Modificar tratamientos asistido por una Inteligencia Artificial (IA).
*   Validar datos mediante Value Objects (IDs, códigos, firmas).
*   Gestionar la comunicación con servicios externos (Servicio Nacional de Salud).

## 🛠️ Tecnologías Utilizadas

*   **Java 21**: Lenguaje de programación (LTS).
*   **Maven**: Gestión de dependencias y construcción del proyecto.
*   **JUnit 5 (Jupiter)**: Framework de pruebas unitarias.
*   **Mockito**: Librería para la creación de dobles de prueba (Mocks/Stubs) para los servicios externos.

## 📂 Estructura del Proyecto

La estructura sigue el estándar de Maven:

```text
Diseny-Proves-Unitaries/
├── src/
│   ├── main/java/
│   │   ├── data/                  # Value Objects (HealthCardID, ProductID...)
│   │   │   └── exceptions/        # Excepciones propias del dominio
│   │   ├── medicalconsultation/   # Lógica de negocio (ConsultationTerminal, MedicalPrescription...)
│   │   └── services/              # Interfaces de servicios externos (HealthNationalService, AI...)
│   └── test/java/
│       ├── data/                  # Tests unitarios de los Value Objects
│       └── medicalconsultation/   # Tests de la lógica de negocio y Mocks
├── pom.xml                        # Configuración de Maven y dependencias
└── README.md                      # Documentación del proyecto
```
🚀 Instalación y Ejecución
Prerrequisitos
Tener instalado Java JDK 21.
Tener instalado Maven (o usar el integrado en IntelliJ/VS Code).
Pasos para ejecutar los tests
Para lanzar todas las pruebas unitarias y verificar que el sistema funciona correctamente, ejecuta el siguiente comando en la terminal (desde la raíz del proyecto):
- mvn test

Si la ejecución es correcta, verás un mensaje de BUILD SUCCESS.

👥 Autores
Grupo de prácticas:

Boulhani Zanzan Hamza

Rosell Abadias Abril

Fernandez Mimbrera Alejandro
