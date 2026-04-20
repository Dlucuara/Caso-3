import java.util.LinkedList;
import java.util.Queue;

public class BuzonDeClasificadores {

    private Queue<Evento> eventos;
    private int capacidad;

    public BuzonDeClasificadores(int capacidad) {
        this.capacidad = capacidad;
        this.eventos = new LinkedList<>();
    }

    public void depositarEventoClasificado(Evento evento) {
        eventos.add(evento);
    }

    public boolean estaLleno() {
        return eventos.size() == capacidad;
    }

    public Evento tomarEventoClasificado() {
        return eventos.poll();
    }
    
}