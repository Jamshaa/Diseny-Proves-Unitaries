# Informe de Diseño y Refactoring

## Criterios de Diseño
Para la implementación de la práctica se han seguido principios de diseño orientado a objetos y patrones GRASP/SOLID:

### 1. Patrones GRASP
*   **Controlador (Controller)**: La clase `ConsultationTerminal` actúa como controlador de fachada para el caso de uso "Supervisar tratamiento". Es el punto de entrada para los eventos de la interfaz de usuario y orquesta la lógica delegando en las entidades y servicios.
*   **Alta Cohesión (High Cohesion)**: Las clases tienen responsabilidades claras. `MedicalPrescription` gestiona las líneas de prescripción, mientras que `HealthNationalService` se encarga exclusivamente de la comunicación externa.
*   **Bajo Acoplamiento (Low Coupling)**: El uso de interfaces (`HealthNationalService`, `DecisionMakingAI`) desacopla el controlador de las implementaciones concretas, facilitando el testing mediante Mocks (Inversión de Dependencias).
*   **Creador (Creator)**: `MedicalPrescription` es responsable de crear las instancias de `MedicalPrescriptionLine` (representadas internamente en el Map) ya que es quien las contiene y gestiona.

### 2. Principios SOLID
*   **SRP (Single Responsibility Principle)**: Cada clase tiene una única razón para cambiar. Por ejemplo, las clases del paquete `data` solo almacenan y validan datos.
*   **DIP (Dependency Inversion Principle)**: `ConsultationTerminal` depende de abstracciones (interfaces) y no de clases concretas para los servicios externos.

### 3. Refactoring y Code Smells
Se han aplicado las siguientes mejoras respecto a un diseño inicial ingenuo:
*   **Primitive Obsession**: Se han encapsulado los identificadores (`HealthCardID`, `ProductID`, etc.) en clases propias (Value Objects) en lugar de usar `String` directamente. Esto centraliza la validación y evita errores de paso de parámetros.
*   **Inmutabilidad**: Las clases de datos son inmutables (`final` y sin setters), lo que previene efectos colaterales indeseados y mejora la seguridad en entornos concurrentes.
*   **Consolidación de Excepciones**: Se han unificado múltiples excepciones granularizdas (e.g., `NullHealthCardIDException`, `InvalidHealthCardIDFormatException`) en una única excepción de dominio por clase (`HealthCardIDException` with detailed messages) para reducir la explosión de clases de excepciones.
*   **Uso de Colecciones Eficientes**: Se ha utilizado `HashMap` en `MedicalPrescription` para almacenar las líneas de prescripción, permitiendo acceso O(1) por `ProductID` en lugar de iterar listas (O(n)).

## Testing
Se han utilizado **Dobles de Prueba (Mocks)** con la librería Mockito para el controlador `ConsultationTerminal`. Esto permite:
1.  Simular el comportamiento del `HealthNationalService` (red, base de datos) sin necesidad de una implementación real.
2.  Verificar que el controlador llama a los métodos correctos de los servicios.
3.  Probar escenarios de error (excepciones) de forma determinista.
