import java.util.LinkedList;
import java.util.Queue;

public class BuzonDeAlertas {
    private Queue<Evento> eventos;

        public BuzonDeAlertas() {
            this.eventos = new LinkedList<>();
        }
    public synchronized void depositarEvento(Evento evento) {
        eventos.add(evento);
        notifyAll();
    }

    public synchronized boolean estaLleno() {
        return false; // nunca se llena porque es ilimitado
    }

    public synchronized Evento tomarEvento() {
         while (eventos.isEmpty()) {
            try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
         }
        Evento e = eventos.poll();
        return e;

    }

    public synchronized void decartarEvento(Evento evento) {
        eventos.remove(evento);

    }
    
}
