package ar.edu.siglo21.sigetec.reportes;

import ar.edu.siglo21.sigetec.modelo.Ticket;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz usando un ARCHIVO CSV.
 *
 * Importante: este archivo NO reemplaza a MySQL. La información oficial
 * sigue en MySQL; el CSV es una copia consultable para fines de reporte.
 * Solo se exporta IDs y campos controlados; evitando colocar diagnósticos,
 * datos personales o contraseñas en un archivo destinado a demostraciones.
 */
public final class ArchivoReporteCsv implements AlmacenamientoReporte {
    private final Path ruta;

    public ArchivoReporteCsv(Path ruta) {
        if (ruta == null) throw new IllegalArgumentException("Falta indicar la ruta del reporte.");
        this.ruta = ruta;
    }

    @Override
    public Path guardar(List<Ticket> tickets) throws IOException {
        if (tickets == null) throw new IllegalArgumentException("La lista es obligatoria.");
        Path directorio = ruta.toAbsolutePath().getParent();
        if (directorio != null) Files.createDirectories(directorio);

        // try-with-resources garantiza el cierre aunque ocurra una excepción.
        try (BufferedWriter salida = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
            salida.write("id_ticket;tipo;prioridad;estado;id_cliente");
            salida.newLine();
            for (Ticket t : tickets) {
                salida.write(t.getId() + ";" + t.getTipo().name() + ";"
                        + t.getPrioridad().name() + ";" + t.getEstado() + ";" + t.getClienteId());
                salida.newLine();
            }
        }
        return ruta.toAbsolutePath().normalize();
    }

    @Override
    public List<String> leer() throws IOException {
        if (!Files.exists(ruta)) {
            throw new IOException("Todavía no existe el CSV. Usá antes la opción de exportación.");
        }
        // Leer el archivo no realiza cambios en la base de datos.
        return new ArrayList<>(Files.readAllLines(ruta, StandardCharsets.UTF_8));
    }
}
