import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntradaEventos {
    private Queue<Evento> eventos;

    public BuzonEntradaEventos() {
        this.eventos = new LinkedList<>();
    }

    public synchronized boolean estaLleno() {
        return false; // nunca se llena porque es ilimitado
    }
    public synchronized boolean estaVacio() {
    return eventos.isEmpty();
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
                return null;
            }
        }
        Evento e = eventos.poll();
        return e;
    }
}
