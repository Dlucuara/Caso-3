import java.util.LinkedList;
import java.util.Queue;

public class BuzonDeConsolidacion {
    private Queue<Evento> eventos;
    private int capacidad;
    private int id;

    public BuzonDeConsolidacion(int id, int capacidad) {
        this.id = id;
        this.capacidad = capacidad;
        this.eventos = new LinkedList<>();
    }

    public void depositarEventoConsolidado(Evento evento) {
        eventos.add(evento);
    }

    public boolean estaLleno() {
        return eventos.size() == capacidad;
    }

    public Evento tomarEventoConsolidado() {
        return eventos.poll();
    }

    public int getId() {
        return id;
    }
    
}
