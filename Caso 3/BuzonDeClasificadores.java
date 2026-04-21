import java.util.LinkedList;
import java.util.Queue;

public class BuzonDeClasificadores {

    private Queue<Evento> eventos;
    private int capacidad = Configuracion.tam1;

    public BuzonDeClasificadores() {
        this.eventos = new LinkedList<>();
    }

    public synchronized void depositarEventoClasificado(Evento evento) {
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

    public boolean estaLleno() {
        return eventos.size() == capacidad;
    }

    public synchronized boolean estaVacio() {
    return eventos.isEmpty();
}

    public synchronized Evento tomarEventoClasificado() {
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
    
}