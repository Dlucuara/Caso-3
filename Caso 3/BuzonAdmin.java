import java.util.LinkedList;
import java.util.Queue;

public class BuzonAdmin {
    private final Queue<Evento> eventos = new LinkedList<>();

    public synchronized void depositarEvento(Evento evento) {
        
        eventos.add(evento);
    }

    public synchronized boolean estaVacio() {
        return eventos.isEmpty();
    }
}