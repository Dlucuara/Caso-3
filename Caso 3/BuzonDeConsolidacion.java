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

    public synchronized void depositarEventoConsolidado(Evento evento) {
        while (estaLleno()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        eventos.add(evento);
        notifyAll();
    }

    public synchronized boolean estaLleno() {
        return eventos.size() == capacidad;
    }

    public synchronized Evento tomarEventoConsolidado() {
        while (eventos.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Evento evento = eventos.poll();
        notifyAll();
        return evento;
    }

    public int getId() {
        return id;
    }

    public synchronized boolean estaVacio() {
    return eventos.isEmpty();
}
    
}
