import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntradaEventos {
    private int capacidadBuzon;
    private Queue<Evento> eventos;

    public BuzonEntradaEventos(int capacidad) {
        this.capacidadBuzon = capacidad;
        this.eventos = new LinkedList<>();
    }

    public synchronized boolean estaLleno() {
        if (eventos.size() >= capacidadBuzon) {
            return true; 
        }
        return false;
    }

    public synchronized void depositarEvento(Evento evento) {
        eventos.add(evento);
        notifyAll();
    }

    public synchronized Evento tomarEvento() {
        while (eventos.isEmpty()) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Evento e = eventos.poll();
        notifyAll();
        return e;
    }
}
