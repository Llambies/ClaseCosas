---
tags:
  - Estudios/DAM/Segundo
---
# MÒDUL PROJECTE -  Índex proposat (esborrany)

## 1. Introducció
- Presentació (i/o motivació) i objectiu del projecte.
- Factor diferenciador del projecte
- Anàlisis de la situació de partida
- Objectius a aconseguir amb el projecte
- Relació amb els continguts dels diferents mòduls

## 2. Presentació de les diverses tecnologies que es poden utilitzar per a la seua realització

### 2.1  Justificació de l’elecció de les tecnologies.

## 3. Anàlisi del projecte

### 3.1. Requeriments funcionals i no funcionals
#### 3.1.1 Requerimientos funcionales

| **Categoría**                | **Requerimiento Funcional Específico**                                                                                                                                  | **Regla de Negocio / Caso de Uso Clave**                                                                                                                                                                                                                                     |
| ---------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Gestión de Cuentas**       | El sistema debe permitir el registro de nuevos usuarios.                                                                                                                | El usuario debe proporcionar una dirección de correo electrónico única y una contraseña segura.                                                                                                                                                                              |
|                              | El sistema debe permitir a los usuarios iniciar y cerrar sesión.                                                                                                        | Se debe implementar autenticación de dos factores opcional (si se considera de alta seguridad).                                                                                                                                                                              |
| **Creación de Ligas**        | El sistema debe permitir a un usuario crear una liga privada o pública.                                                                                                 | El creador de la liga puede establecer reglas como el número máximo de participantes, formato de los combates, restricciones de criaturas.                                                                                                                                   |
|                              | El sistema debe habilitar un formulario para que el creador de la liga establezca su propia lista de criaturas disponibles.                                             | El formulario debe permitir ingresar el **nombre de la criatura**, sus **estadísticas base** y su **lista de movimientos y coste de subasta inicial** (sustituye la dependencia de la API de Pokémon).                                                                       |
|                              | El sistema debe permitir a los usuarios unirse a una liga existente mediante un código de invitación o búsqueda.                                                        | Una liga privada solo es accesible con el código. Una liga pública se llena automáticamente.                                                                                                                                                                                 |
| **Gestión del Equipo**       | El sistema debe permitir a los usuarios fichar criaturas bajo un presupuesto.                                                                                           | Las criaturas se obtienen mediante un evento de subasta inicial. Cada criatura tiene un valor de mercado que fluctúa.                                                                                                                                                        |
|                              | El sistema debe permitir a los usuarios establecer un equipo formado por hasta 6 criaturas.                                                                             | Las criaturas no podrán repetirse, deberán poseer un _moveset_ legal y una configuración. **Gestión de Equipos** El sistema debe permitir al jugador mantener un equipo activo de hasta **6 criaturas**. Las criaturas deben ser adquiridas mediante el sistema de subastas. |
| **Configuración Criatura**   | La interfaz de usuario debe ofrecer _widgets_ interactivos y ergonómicos (ej. _sliders_, selectores) para la asignación de **EVs** y la selección de **Movimientos**.   | Debe implementarse validación básica del lado del cliente para las reglas de entrada (ej. los EVs $\le 510$) antes de enviar la petición a la API.                                                                                                                           |
| **Puntuación y Ranking**     | El sistema debe otorgar puntos diarios basados en el rendimiento del equipo en la simulación.                                                                           | La fórmula de puntos debe recompensar el Daño Infligido, las Victorias, y penalizar las Derrotas/Debilitaciones.                                                                                                                                                             |
| **Transacciones**            | El sistema debe permitir a los usuarios fichar criaturas de la "Agencia Libre" (no seleccionadas) o hacer traspasos con otros usuarios.                                 | Los fichajes de la Agencia Libre se manejan por prioridad de _waiver_ o subasta de presupuesto.                                                                                                                                                                              |
| **Notificaciones**           | El sistema debe notificar al usuario sobre los resultados de la jornada y cualquier cambio importante en el mercado de criaturas.                                       | Las notificaciones deben ser _push_ (móvil) y/o por correo electrónico.                                                                                                                                                                                                      |
| **Mercado de Subastas**      | El sistema debe actualizar el mercado diariamente con una rotación de nuevas criaturas disponibles para su adquisición.                                                 | El mercado se habilita después de la simulación de combate (12:00 CET).                                                                                                                                                                                                      |
|                              | El sistema debe permitir a los usuarios **pujar** por las criaturas disponibles utilizando la moneda del juego.                                                         | Las subastas deben tener un límite de tiempo claro y un sistema de puja ciega o subasta silenciosa para evitar la manipulación en el último segundo.                                                                                                                         |
|                              | El sistema debe notificar al usuario si ganó o perdió la puja por una criatura.                                                                                         | Si gana, la criatura se añade al equipo y el presupuesto se deduce automáticamente.                                                                                                                                                                                          |
| **Finanzas del Juego**       | El sistema debe llevar un registro claro del presupuesto de cada jugador (moneda del juego) para participar en las subastas.                                            | Los jugadores reciben una asignación inicial y pueden ganar más moneda con las victorias diarias.                                                                                                                                                                            |
| **Simulación Diaria**        | El sistema debe ejecutar una simulación de combate (PVP o PVE aleatorio) para cada equipo de la liga, **diariamente a una hora fija** (e.g., 23:00 CET).                | La simulación debe utilizar las configuraciones de _Naturaleza, EVs, Moveset_ y _Objeto_ definidas por el usuario para calcular el resultado.                                                                                                                                |
|                              | El sistema debe generar un **reporte de combate** que muestre el desarrollo turno a turno (movimientos utilizados, daño infligido/recibido, efectos de estado).         | El usuario debe poder acceder a este reporte para entender la puntuación diaria obtenida.                                                                                                                                                                                    |
| **Consumo de API**           | La aplicación debe consumir los _endpoints_ **REST** proporcionados por la API de Node.js para obtener y enviar datos.                                                  | Uso de librerías como `http` o `dio` en Flutter para manejar peticiones y respuestas **JSON**.                                                                                                                                                                               |
| **Estado Local (Gestión)**   | La aplicación debe gestionar eficientemente el estado de la aplicación (equipo actual, presupuesto, ranking de liga) para minimizar las llamadas a la API.              | Implementación de un sistema de gestión de estado robusto (ej. **Provider, Riverpod, o Bloc**) para sincronizar la UI con la API.                                                                                                                                            |
| **Diseño Adaptativo**        | La aplicación de Flutter debe ofrecer una interfaz de usuario óptima y adaptable, tanto en **escritorio** (monitores grandes) como en **móviles** (pantallas pequeñas). | Uso de _widgets_ de diseño responsivo (ej. `LayoutBuilder`, `MediaQuery`) para ajustar la disposición y el tamaño de los elementos.                                                                                                                                          |
| **Visualización de Combate** | La aplicación debe ser capaz de mostrar los reportes de combate simulado (JSON) de forma visualmente atractiva y fácil de leer.                                         | Uso de animaciones _widgets_ de línea de tiempo para presentar el log del combate turno por turno.                                                                                                                                                                           |
#### 3.1.2. Requerimientos no funcionales
Estos requerimientos definen los atributos de calidad del sistema, enfocándose en la experiencia del usuario y la robustez técnica.
##### 3.1.2.1. Requerimientos de Rendimiento

| **Requerimiento**                         | **Métrica Específica**                                                                                                                                            | **Importancia**                                                                      |
| ----------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------ |
| **Tiempo de Respuesta (Carga de Tablas)** | La tabla de clasificación de la liga debe cargar en menos de **2 segundos**, incluso con 200 ligas activas.                                                       | Crítica, afecta la experiencia de usuario.                                           |
| **Rendimiento (Transacciones)**           | La actualización semanal de puntos de todos los equipos en todas las ligas debe completarse en menos de **30 minutos**.                                           | Alta, asegura la disponibilidad de resultados oportunos.                             |
| **Concurrencia (Draft)**                  | El sistema debe manejar al menos **1,000 usuarios concurrentes** durante el evento de _draft_ sin fallos.                                                         | Crítica, ya que el _draft_ es un momento de alta demanda.                            |
| **Timing de Simulación**                  | El motor de simulación debe ser capaz de procesar todos los combates y calcular los puntos en un _tiempo máximo de 30 minutos_ tras la hora de inicio programada. | **Crítica**, los usuarios necesitan ver los resultados y el nuevo mercado a tiempo.  |
| **Latencia de Configuración**             | El guardado y validación de las configuraciones avanzadas de un Pokémon (EVs, Moveset) debe tomar menos de **500 milisegundos**.                                  | **Alta**, una interfaz lenta desincentivará a los usuarios a realizar ajustes finos. |
| **Actualización de Ranking**              | El ranking de la liga debe mostrar los puntos del día actual dentro de **5 minutos** de finalizar el proceso de simulación.                                       | **Alta**, los resultados deben ser inmediatos después del proceso _batch_.           |
| **Rendimiento de Subastas**               | El sistema debe manejar 1000 ofertas por segundo durante los últimos 5 minutos del período de subasta sin experimentar errores ni _timeouts_.                     | **Crítica**, momento de alta concurrencia.                                           |
##### 3.1.2.2. Requerimientos de Seguridad

| **Requerimiento**                | **Descripción**                                                                                                                                | **Medidas Clave**                                                                           |
| -------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------- |
| **Autenticación y Autorización** | Solo los usuarios registrados y verificados deben acceder a los datos de las ligas.                                                            | Uso de **OAuth 2.0** y almacenamiento de contraseñas mediante _hashing_ (e.g., **bcrypt**). |
| **Protección de Datos**          | La información personal (correos) y financiera (si hubiera) debe estar protegida.                                                              | **Cifrado** de datos en reposo y en tránsito (**TLS/SSL**).                                 |
| **Prevención de Trampas**        | Las reglas de la liga (ej., límite de presupuesto) deben ser implementadas en el _back-end_ para evitar manipulación del cliente.              | **Validación estricta del lado del servidor** de todas las transacciones de mercado.        |
| **Integridad de la Simulación**  | El código del motor de simulación y la lógica de puntuación deben ser inalterables y ejecutarse en un entorno seguro para evitar trampas.      | **Validación de Checksum** o _Hash_ del código del motor de simulación.                     |
| **Seguridad de Transacciones**   | El sistema de subastas debe garantizar que la deducción de presupuesto y la asignación del Pokémon sean transacciones atómicas e irrefutables. | Uso de **Transacciones de Base de Datos** (ACID) para el sistema de pujas.                  |
##### 3.1.2.3. Requerimientos de Calidad (Usabilidad, Confiabilidad, Mantenibilidad)

| **Tipo de Calidad**           | **Requerimiento Específico**                                                                                                                                | **Impacto**                                                                    |
| ----------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------ |
| **Usabilidad (UI/UX)**        | El diseño de la aplicación debe ser **intuitivo**, permitiendo al usuario configurar su alineación semanal en 3 clics o menos.                              | Directo en la satisfacción del cliente.                                        |
| **Confiabilidad**             | El tiempo de actividad (uptime) del servidor debe ser de al menos **99.9%** (menos de 8.76 horas de inactividad anual).                                     | Crítica, especialmente en días de actualización de resultados.                 |
| **Mantenibilidad**            | El código base debe seguir los principios **SOLID** y estar documentado para que un nuevo desarrollador pueda corregir un _bug_ menor en menos de 4 horas.  | Reduce el costo a largo plazo del desarrollo y la corrección de errores.       |
| **Plataformas**               | La aplicación debe ser completamente funcional en **iOS (16+)**, **Android (12+)** y navegadores web modernos (Chrome, Safari, Firefox).                    | Asegura la máxima accesibilidad para los usuarios.                             |
| **Fiabilidad del Scheduler**  | El servicio que dispara la simulación diaria y la apertura de subastas debe tener redundancia para garantizar su ejecución incluso si un servidor cae.      | **Crítica**, si el _scheduler_ falla, el juego se detiene.                     |
| **Mantenibilidad del Motor**  | El motor de combate debe ser modular, permitiendo la fácil adición de nuevas reglas de juego, Pokémon y movimientos sin afectar el núcleo de la simulación. | Facilita la introducción de nuevas "Generaciones" o ajustes de balance.        |
| **Usabilidad de la Interfaz** | El diseñador de Pokémon (para EVs/Moves) debe ser visual y preventivo, advirtiendo al usuario de _builds_ no legales antes de intentar guardar.             | Mejora la experiencia, reduciendo la frustración por errores de configuración. |

### 3.2. Temporalització del projecte
##### Hito 1: Arquitectura Base y Gestión de Usuarios (MVP Inicial
**Objetivo:** Establecer los cimientos tecnológicos y permitir el acceso seguro a la aplicación.
- **Backend (Node.js):**
	- Configuración del servidor y conexión a Base de Datos.
	- Implementación de seguridad: Hashing de contraseñas (bcrypt) y Tokens de sesión (JWT/OAuth 2.0).
	- API Endpoints para registro y login.
- **Frontend (Flutter):**
	- Estructura base del proyecto y configuración de rutas/navegación.
	- Pantallas de Login, Registro y Perfil de Usuario.
	- Gestión de estado global (Provider/Riverpod) para la sesión del usuario.
- **Entregable:** Aplicación donde un usuario puede registrarse, loguearse y cerrar sesión de forma segura.
##### Hito 2: Ligas y Gestión de Datos Maestros (Criaturas)
**Objetivo:** Permitir la creación de entornos de juego (Ligas) y la definición de las "fichas" de juego (Criaturas base).
- **Backend:**
	- CRUD de Ligas (Crear pública/privada, generar códigos de invitación).
	- CRUD de Criaturas (Interfaz de administrador para ingresar stats base, movimientos y coste inicial).
- **Frontend:**
	- Pantalla de "Lobby": Ver ligas disponibles, unirse por código o crear una nueva.
	- Visualización del listado de criaturas disponibles (Pokedex interna).
- **Entregable:** Usuarios pueden crear ligas, invitar amigos y ver la base de datos de criaturas existentes.
##### **Hito 3: Gestión de Equipos y Configuración Avanzada (Core Gameplay I)**
**Objetivo:** Desarrollar la lógica de gestión de equipos y la personalización técnica de las criaturas (EVs/Movesets).
- **Backend:**
	- Validación de reglas de equipo (máximo 6 criaturas, no repetidas).
	- Validación de Movesets legales y EVs <= 510.
- **Frontend:**
	- Implementación de la interfaz "Drag & Drop" o selectores para armar el equipo.
	- **Diseño de UI Compleja:** Desarrollo de _sliders_ y _widgets_ ergonómicos para asignar EVs y seleccionar ataques.
	- Validación en cliente (feedback inmediato) antes de guardar cambios.
- **Entregable:** El usuario puede conformar un equipo válido y personalizar las estadísticas de sus criaturas.
##### Hito 4: Economía y Sistema de Subastas (Core Gameplay II)
**Objetivo:** Implementar la lógica transaccional y el mercado dinámico.
- **Backend:**
	- Lógica de presupuesto y monedero del jugador.
	- Sistema de **Subastas**: Temporizadores, gestión de pujas ciegas y resolución automática al finalizar el tiempo.
	- Transacciones atómicas (ACID) para asegurar que no se dupliquen criaturas ni dinero.
- **Frontend:**
	- Pantalla de "Mercado/Agencia Libre" con filtros.
	- Interfaz de pujas en tiempo real (o con actualización frecuente).
	- Notificaciones de "Puja Ganada" o "Saldo Insuficiente".
- **Entregable:** Un mercado funcional donde los usuarios compiten por criaturas usando el presupuesto ficticio.
##### Hito 5: Motor de Simulación y Visualización (Factor Diferenciador)
**Objetivo:** Automatizar la resolución de combates y mostrar resultados atractivos.
- **Backend (Scheduler & Engine):**
	- Implementación del **Scheduler** (Cron job) para ejecutar tareas a las 23:00 CET.
	- **Motor de Combate:** Script que toma dos equipos (JSON), calcula daño/estados turno a turno y genera un BattleLog (JSON) y el resultado final.
	- Cálculo de Puntuación y actualización del Ranking diario.
- **Frontend:**
	- Parsing del BattleLog JSON.
	- Visualización del combate: Línea de tiempo interactiva o animaciones simples que muestren qué ocurrió en cada turno.
	- Pantalla de Clasificación/Ranking actualizada.
- **Entregable:** El sistema juega solo a la hora programada y los usuarios pueden ver la repetición y sus nuevos puntos al día siguiente.
##### Hito 6: Optimización, No Funcionales y Despliegue
**Objetivo:** Pulir la aplicación para cumplir con los estándares de calidad y rendimiento definidos.
- **Rendimiento:**
	- Optimización de consultas a BD para cargar rankings en < 2s.
	- Pruebas de carga (concurrencia) para el sistema de subastas.
- **UX/UI:**
	- Ajustes de **Diseño Adaptativo** para asegurar funcionamiento perfecto en Móvil vs. Escritorio.
	- Implementación final de Notificaciones Push (inicio de liga, fin de subasta).
- **Cierre:**
	- Corrección de bugs (Testing).
	- Documentación de código y manual de usuario.
- **Entregable:** Versión final (Release Candidate) lista para defensa del proyecto.
#### Diagrama de Gantt
![[image-17.png]]
### 3.3. Casos d’ús
- Diagrama de casos d’ús
- Descripció dels casos d’ús

### 3.4. Diagrama de classes inicial
- Diagrama de classes
- Descripció de les classes
- Diagrama entitat-relació (si escau)

## 3.5. Mockups d’interfícies

### 3.6. Altres diagrames i descripcions (si escau)

## 4. Disseny del projecte
### 4.1. Arquitectura del sistema
- Descripció de l’arquitectura
- Diagrama de l’arquitectura
- Diagrama de desplegament

### 4.2. Diagrama de classes definitiu
- Diagrama de classes
- Descripció de les classes
- Diagrama entitat-relació (si escau)

### 4.3. Disseny de la interfície d’usuari
- Wireframes
- Diagrama de navegació


### 4.4. Altres diagrames i descripcions (si escau)


## 5. Implementació del projecte
- Estructura del projecte
- Descripció dels mòduls i components principals
- Desplegament de l’aplicació
- Captures de pantalla i exemples de codi (el codi es recomana ficar-ho als annexes)

## 6. Estudi dels resultats obtinguts
- Avaluació del projecte respecte als objectius inicials
- Problemes trobats i solucions aplicades
- Futures millores i ampliacions

## 7. Conclusions
- Relacions amb els continguts dels diferents mòduls
- Valoració personal del projecte


## 8. Bibliografia i recursos utilitzats

## 9. Annexes
- Codi font complet del projecte
- Guia d’instal·lació i ús
- Documentació addicional
- Altres materials rellevants


