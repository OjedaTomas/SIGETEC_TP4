package ar.edu.siglo21.sigetec.reportes;

import ar.edu.siglo21.sigetec.modelo.Prioridad;
import ar.edu.siglo21.sigetec.modelo.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplo de ARREGLOS + ARRAYLIST trabajando juntos.
 *
 * el ArrayList es una carpeta donde la cantidad de tickets
 * puede crecer. El arreglo int[] es una caja con tres casilleros fijos:
 * ALTA, MEDIA y BAJA. Recorremos los tickets y contamos en cada casillero.
 *
 * Es código de análisis, no abre conexiones ni modifica MySQL.
 */
public final class AnalizadorPrioridades {
    private AnalizadorPrioridades() { }

    public static List<String> resumir(List<Ticket> tickets) {
        if (tickets == null) throw new IllegalArgumentException("La lista de tickets no puede ser nula.");

        // ARREGLO: tres prioridades conocidas desde el principio.
        Prioridad[] niveles = { Prioridad.ALTA, Prioridad.MEDIA, Prioridad.BAJA };
        int[] cantidades = new int[niveles.length];

        // ARRAYLIST: permite guardar cualquier cantidad de tickets recibidos.
        ArrayList<Ticket> lista = new ArrayList<>(tickets);
        for (Ticket ticket : lista) {
            for (int i = 0; i < niveles.length; i++) {
                if (ticket.getPrioridad() == niveles[i]) {
                    cantidades[i]++;
                    break;
                }
            }
        }

        // Convertimos el resultado en líneas fáciles de leer desde la consola.
        ArrayList<String> resultado = new ArrayList<>();
        for (int i = 0; i < niveles.length; i++) {
            resultado.add(niveles[i].name() + ": " + cantidades[i] + " ticket(s)");
        }
        resultado.add("TOTAL: " + lista.size() + " ticket(s)");
        return resultado;
    }
}
