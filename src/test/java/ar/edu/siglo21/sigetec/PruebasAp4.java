package ar.edu.siglo21.sigetec;

import ar.edu.siglo21.sigetec.modelo.*;
import ar.edu.siglo21.sigetec.reportes.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Pruebas AP4 independientes de MySQL: no requieren contraseñas ni servidor. */
public class PruebasAp4 {
    private static int aprobadas = 0;
    private static void verificar(boolean ok, String nombre) {
        if (!ok) throw new AssertionError("No pasó: " + nombre);
        System.out.println("OK - " + nombre);
        aprobadas++;
    }
    public static void main(String[] args) throws IOException {
        Ticket alta = FabricaTickets.crear(1, 1, TipoServicio.CCTV,
                "Cámara sin imagen", "Sector demostración", Prioridad.ALTA, "ABIERTO");
        Ticket media = FabricaTickets.crear(2, 1, TipoServicio.REDES,
                "Punto de red inactivo", "Puesto de prueba", Prioridad.MEDIA, "CERRADO");
        Ticket baja = FabricaTickets.crear(3, 1, TipoServicio.SOPORTE_IT,
                "PC lenta", "PC ficticia", Prioridad.BAJA, "ABIERTO");
        ArrayList<Ticket> datos = new ArrayList<>(List.of(alta, media, baja, alta));
        verificar(datos.size() == 4, "ArrayList almacena cuatro objetos");
        List<String> resumen = AnalizadorPrioridades.resumir(datos);
        verificar(resumen.get(0).equals("ALTA: 2 ticket(s)"), "Contador en arreglo para ALTA");
        verificar(resumen.get(1).equals("MEDIA: 1 ticket(s)"), "Contador en arreglo para MEDIA");
        verificar(resumen.get(2).equals("BAJA: 1 ticket(s)"), "Contador en arreglo para BAJA");
        verificar(resumen.get(3).equals("TOTAL: 4 ticket(s)"), "El resumen conserva el total");
        verificar(AnalizadorPrioridades.resumir(List.of()).get(3).contains("0"), "Soporta lista vacía");
        try {
            AnalizadorPrioridades.resumir(null);
            throw new AssertionError("Debe fallar ante lista nula");
        } catch (IllegalArgumentException ex) {
            verificar(true, "Valida lista nula");
        }
        Path carpeta = Files.createTempDirectory("sigetec-ap4-");
        Path ruta = carpeta.resolve("reporte.csv");
        AlmacenamientoReporte archivo = new ArchivoReporteCsv(ruta);
        verificar(!Files.exists(ruta), "El reporte comienza sin crear");
        try {
            archivo.leer();
            throw new AssertionError("Debe fallar cuando no existe CSV");
        } catch (IOException ex) {
            verificar(ex.getMessage().contains("no existe"), "Manejo de excepción CSV inexistente");
        }
        verificar(archivo.guardar(datos).equals(ruta.toAbsolutePath()), "Exporta y devuelve ruta");
        List<String> filas = archivo.leer();
        verificar(filas.size() == 5, "Lee cabecera y cuatro registros");
        verificar(filas.get(0).equals("id_ticket;tipo;prioridad;estado;id_cliente"), "CSV con encabezados");
        verificar(filas.get(1).equals("1;CCTV;ALTA;ABIERTO;1"), "Registro escrito correctamente");
        verificar(Files.readString(ruta, StandardCharsets.UTF_8).contains("SOPORTE_IT"),
                "Contenido persistido y recuperable");
        archivo.guardar(List.of(media));
        verificar(archivo.leer().size() == 2, "Segunda exportación reemplaza la primera");
        verificar(alta instanceof Ticket && media instanceof Ticket, "Polimorfismo sigue disponible");
        Files.deleteIfExists(ruta);
        Files.deleteIfExists(carpeta);
        System.out.println("\nPRUEBAS AP4 APROBADAS: " + aprobadas);
    }
}
