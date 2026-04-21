import java.util.LinkedList;
import java.util.Queue;

public class BuzonDeClasificadores {

    private Queue<Evento> eventos;
    private int capacidad = Configuracion.tam1;

    public BuzonDeClasificadores() {
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