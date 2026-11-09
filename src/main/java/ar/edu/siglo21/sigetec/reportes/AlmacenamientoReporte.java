package ar.edu.siglo21.sigetec.reportes;

import ar.edu.siglo21.sigetec.modelo.Ticket;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * INTERFAZ: define qué debe poder hacer un sistema que guarda reportes.
 * No dice cómo se guarda: hoy usamos CSV, mañana podría ser otro formato.
 * Es una pequeña abstracción orientada a la posibilidad de ampliar SIGETEC.
 */
public interface AlmacenamientoReporte {
    Path guardar(List<Ticket> tickets) throws IOException;
    List<String> leer() throws IOException;
}
