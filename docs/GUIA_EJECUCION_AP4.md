# SIGETEC - Guía de ejecución AP4 en NetBeans y MySQL

## 1. Qué conservar del TP3
El AP4 es un **incremento** sobre el TP3. Si tu MySQL ya tiene `sigetec` con siete tablas y las consultas funcionaban, **no vuelvas a ejecutar el script de creación para borrar o reemplazar datos**. Las siete tablas mantienen el esquema original.

## 2. Crear una copia de seguridad antes de actualizar
En MySQL Workbench: **Server > Data Export > sigetec > Export to Self-Contained File > Start Export**. Así conservás un respaldo. El proyecto no trae contraseñas ni registros reales de clientes.

## 3. Preparar la nueva carpeta
Descomprimí `SIGETEC_AP4_INTEGRADOR.zip` en tu computadora, por ejemplo `Documentos/SIGETEC_AP4_INTEGRADOR`.

**Si usás una carpeta NUEVA:** copiá **solo tu propio** `config/sigetec.properties` desde tu carpeta TP3 a la subcarpeta `config/` de AP4. No publiques este archivo. El resto del proyecto ya está dentro del ZIP.

**Si preferís actualizar la carpeta TP3 que ya subiste a GitHub:** hacé una copia de esa carpeta y después copiá desde AP4 las carpetas `src/`, `diagramas/`, `docs/`, `sql/`, más `pom.xml`, `.gitignore` y `README.md`. No reemplaces tu `config/sigetec.properties` real. Verificá `git status` antes de publicar.

## 4. Abrir NetBeans
En NetBeans, `File > Open Project` y elegí la carpeta `SIGETEC_AP4_INTEGRADOR` que contiene `pom.xml`. Hacé `Clean and Build` y luego `Run` o F6. Usá Java 17 o superior. Maven necesita haber descargado MySQL Connector/J.

La consola primero debe indicar `Conexión MySQL: correcta. Base de datos: sigetec`.

## 5. Usar las tres opciones nuevas
- **13. Resumen por prioridad:** lee los tickets desde MySQL, crea un `ArrayList<Ticket>` y usa arreglos `Prioridad[]` e `int[]` para contar prioridades.
- **14. Guardar reporte de tickets en CSV:** guarda `exportaciones/reporte_tickets.csv` con ID, tipo, prioridad, estado e ID de cliente. No guarda diagnósticos ni contraseñas.
- **15. Leer último reporte CSV:** lee y presenta el archivo anterior. Si no existe, muestra un error controlado; primero ejecutá 14.

Las opciones 1 a 12 siguen teniendo la misma finalidad que en AP3, incluida la transacción que finaliza la orden junto al ticket.

## 6. Consultas SQL para la evidencia
Abrí `sql/06_consultas_integrador_ap4.sql` en MySQL Workbench y ejecutalo. Es solo lectura; permite comprobar tickets por prioridad y las relaciones cliente-ticket-orden-técnico-intervención.

## 7. Pruebas automáticas sin MySQL
Abrí PowerShell en la carpeta de AP4 y ejecutá:

```powershell
New-Item -ItemType Directory -Force -Path prueba-clases | Out-Null
$archivos = Get-ChildItem src/main/java,src/test/java -Filter *.java -Recurse | ForEach-Object FullName
javac -encoding UTF-8 --release 17 -d prueba-clases $archivos
java -cp prueba-clases ar.edu.siglo21.sigetec.PruebasModelo
java -cp prueba-clases ar.edu.siglo21.sigetec.PruebasAp4
```

En la validación de este paquete pasaron **16 pruebas del modelo anterior + 16 pruebas nuevas de AP4**. Esta prueba sin servidor no sustituye verificar JDBC en tu equipo.

## 8. Capturas a generar para el informe y el video
1. NetBeans con clase `AnalizadorPrioridades.java`, donde se vea `int[]` y `ArrayList<Ticket>`.
2. Menú AP4 con opciones 13, 14 y 15.
3. Resultado de opción 13 con tickets reales de la base.
4. Opción 14 generando archivo CSV y Explorador/Bloc de notas mostrando su contenido.
5. Opción 15 recuperando el archivo.
6. MySQL Workbench con las siete tablas y los tickets correspondientes.
7. GitHub con el commit AP4 y archivos nuevos visibles.

No utilices en capturas públicas nombres de clientes reales, ubicaciones de cámaras de seguridad, credenciales ni diagnósticos sensibles. Usá ejemplos ficticios o censurá información.

## 9. GitHub - agregar el AP4 al repositorio existente
Tu repositorio de TP3 es `https://github.com/OjedaTomas/SIGETEC_TP3`. El **AP4 no queda publicado automáticamente** por descargar este ZIP. En la carpeta de tu repositorio clonado, después de copiar y probar los cambios, ejecutá:

```powershell
git status --ignored
git add .
git status
git commit -m "AP4 integrador - Repository, arrays, ArrayList y archivos CSV"
git push origin main
```

Antes del `git add`, verificá que **`config/sigetec.properties`** y **`exportaciones/`** figuren como ignorados; también evitá copiar una carpeta del proyecto anidada dentro de sí misma.

## 10. Video de aproximadamente tres minutos
Seguí `docs/GUION_VIDEO_AP4_3_MINUTOS.md`. Se debe mostrar la **ejecución real** en NetBeans, SQL y el CSV. El video no está grabado dentro de este paquete.

## 11. Errores comunes
- `No se pudo conectar a MySQL`: comprobá servidor iniciado, URL, usuario y contraseña local.
- `Access denied`: revisá credenciales de `config/sigetec.properties` y permisos de `sigetec_app`.
- Opción 15 dice que no existe CSV: ejecutá 14 primero.
- El CSV aparece en otra carpeta: se genera relativo a la carpeta de trabajo del proyecto (`exportaciones/`).
- Se muestran 0 tickets: en MySQL no hay tickets o se consultó otra base.
- Git quiere subir contraseña: revisá `.gitignore` y ejecutá `git status --ignored`; nunca uses `git add -f` sobre credenciales.
