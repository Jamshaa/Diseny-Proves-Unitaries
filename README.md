# 🩺 Gestión de Consultas Médicas — Supervisar Tratamiento

Este repositorio forma parte de un sistema de gestión de consultas médicas, centrado en el caso de uso “Supervisar Tratamiento”.
El objetivo principal del proyecto es modelar de forma clara y segura cómo un facultativo revisa el estado clínico de un paciente, gestiona sus tratamientos activos y emite nuevas prescripciones, todo ello con una base sólida de diseño y pruebas.

El proyecto está pensado tanto como ejercicio de ingeniería de software bien hecha como base realista de un sistema clínico.

## 🚀 ¿Qué hace este proyecto?

El sistema permite al profesional sanitario:
*   Consultar el historial médico del paciente.
*   Revisar y modificar prescripciones vigentes.
*   Crear nuevas recetas de forma controlada.
*   Validar el proceso mediante servicios externos (SNS, IA clínica, firma digital).

Todo el flujo está protegido por estados y reglas claras para evitar errores médicos o inconsistencias.

## ✨ Funcionalidades principales

### Gestión del estado de la consulta
La consulta pasa por distintos estados (READY, EDITING, SIGNED, etc.), lo que garantiza que cada acción solo pueda realizarse cuando corresponde.

### Gestión de recetas y medicación
Alta, modificación y eliminación de líneas de medicación, con validaciones de posología y reglas de negocio bien definidas.

### Integración con servicios externos
Uso de interfaces para desacoplar el sistema del:
*   Sistema Nacional de Salud
*   Servicios de soporte basados en Inteligencia Artificial

### Firma digital de recetas
Soporte para la validación y estampado de firmas electrónicas antes de cerrar una prescripción.

## 🏗️ Diseño y arquitectura

El proyecto pone especial cuidado en cómo está construido, no solo en que funcione.

### Principios SOLID
*   Responsabilidad única (SRP) bien definida.
*   Inversión de dependencias (DIP) para facilitar el testeo y la evolución del sistema.

### Patrones GRASP
*   `ConsultationTerminal` actúa como Controller.
*   Las entidades de dominio siguen el patrón Information Expert.

### Value Objects
Uso de objetos inmutables para representar datos críticos del dominio, como:
*   `HealthCardID`
*   `ProductID`
*   `DigitalSignature`

Esto reduce errores y mejora la claridad del modelo.

## 📂 Estructura del proyecto
```text
├── Diseny-Proves-Unitaries/   # Código fuente principal
│   ├── data/                 # Value Objects e identificadores
│   ├── medicalconsultation/  # Lógica de dominio y excepciones
│   └── services/             # Interfaces de servicios externos
└── test/                     # Pruebas unitarias
    ├── data/                 # Tests de validación de IDs
    └── medicalconsultation/  # Tests de lógica de negocio y terminal
```

La separación entre dominio, servicios y pruebas está pensada para que el código sea fácil de entender, probar y mantener.

## 🛠️ Tecnologías utilizadas

*   **Java** — Lenguaje principal del proyecto.
*   **JUnit 5** — Ejecución de pruebas unitarias.
*   **Mockito** — Creación de mocks para simular servicios externos.
*   **Maven** — Gestión de dependencias y ciclo de vida del proyecto.

## 🧪 Pruebas

La calidad del sistema se apoya en una suite de pruebas unitarias completa.

Para ejecutarlas:
```bash
mvn test
```

Las pruebas cubren:
*   Flujos normales (happy path).
*   Estados inválidos.
*   Excepciones y violaciones de reglas de negocio.

> [!TIP]
> 💡 La idea no es solo comprobar que “funciona”, sino demostrar que el sistema falla bien cuando debe hacerlo.
