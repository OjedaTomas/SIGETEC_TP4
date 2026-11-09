# SIGETEC - AP4 Integrador final

**Sistema Integral de Gestión de Servicios Técnicos**  
**Autor:** Tomás Ojeda | **Carrera:** Licenciatura en Informática  
**Materia:** Seminario de Práctica de Informática

## Continuidad del proyecto
Este es el incremento AP4 del prototipo SIGETEC desarrollado en los TP anteriores. Conserva los módulos que ya funcionaban con MySQL y agrega tres funciones: **resumen por prioridad con arreglos y `ArrayList`**, **exportación CSV** y **lectura del último reporte CSV**.

El prototipo es un programa Java de consola, no la aplicación web final. Su base de datos oficial sigue siendo MySQL. El CSV es solamente un reporte local.

## Patrón de diseño adoptado
**Repository**, representado por `datos/Repositorio.java`, concentra las consultas JDBC de MySQL. `servicio/ServicioSigetec.java` concentra las reglas de negocio y `consola/Main.java` muestra el menú. Como complemento, `modelo/FabricaTickets.java` usa una *fábrica simple* para elegir la subclase de `Ticket`.

## Funciones del menú
Opciones 1-12: registro de clientes y técnicos, tickets, órdenes, asignaciones, intervenciones, cierre, búsquedas, ordenación y seguimiento del historial.

**Opciones nuevas AP4:**
- **13** Resumen de tickets por prioridad (`ArrayList<Ticket>` + `Prioridad[]` + `int[]`).
- **14** Guardar reporte CSV (`exportaciones/reporte_tickets.csv`).
- **15** Leer reporte CSV previamente guardado.

## Requisitos
Java 17+, Apache NetBeans con soporte Maven, MySQL Server 8+ y MySQL Workbench. El controlador JDBC se gestiona con Maven mediante `pom.xml`.

## Iniciar en un MySQL ya creado para el TP3
No hace falta recrear la base. Usá la misma `sigetec` con sus **7 tablas**. Copiá tu archivo local `config/sigetec.properties` del proyecto anterior dentro de la carpeta AP4, sin publicarlo. Abrí la carpeta que contiene `pom.xml` en NetBeans, hacé **Clean and Build** y ejecutá con **F6**.

Si necesitás instalar desde cero: ejecutá en Workbench `sql/01_crear_base.sql` y `sql/02_datos_referencia.sql`; copiá `config/sigetec.properties.ejemplo` a `config/sigetec.properties` y configurá credenciales. `sql/05_usuario_opcional.sql` permite crear un usuario de privilegios limitados si tenés permisos administrativos.

Para obtener las consultas de evidencias de AP4 ejecutá `sql/06_consultas_integrador_ap4.sql` (**solo lectura**).

## Verificaciones
Código Java compilado con `javac --release 17`. **32 pruebas de lógica completadas**: 16 del modelo original y 16 nuevas de AP4. Las pruebas de AP4 no requieren servidor MySQL y verifican la combinación de estructuras, lectura y escritura de CSV y excepciones de archivo. La prueba real JDBC de AP4 debe hacerse en la computadora donde corre MySQL.

## Archivos
- `src/main/java`: clases de dominio, `Repositorio`, `ServicioSigetec`, menú y funciones AP4.
- `src/test/java`: pruebas independientes de MySQL.
- `sql`: scripts del esquema TP3 y consultas nuevas AP4.
- `diagramas`: imágenes y fuentes `.dot` de arquitectura, clases y DER.
- `docs`: informe editable/PDF, guía de ejecución y guion del video.
- `evidencias`: capturas del TP3 usadas como antecedente, anonimizadas cuando correspondía.
- `config/sigetec.properties.ejemplo`: plantilla sin contraseña.

**No publicar:** `config/sigetec.properties`, `target/`, `exportaciones/`, archivos con credenciales ni información operativa de clientes reales. `.gitignore` los excluye.

## Ejecutar y documentar
Leé `docs/GUIA_EJECUCION_AP4.md` y `docs/GUION_VIDEO_AP4_3_MINUTOS.md`.

## GitHub
Repositorio original para actualizar con AP4: https://github.com/OjedaTomas/SIGETEC_TP3

**Importante:** el repositorio anterior no se actualiza solo. Debés subir los nuevos archivos AP4 y verificar el commit antes de entregar el enlace como versión integradora.
