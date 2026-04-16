import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class Administrador extends Thread {
    private int id;
    private BuzonDeAlertas buzonDeAlertas;
    private BuzonDeClasificadores buzonDeClasificadores;
    private Queue<Evento> eventosRecibidos;

    public Administrador(int id, BuzonDeAlertas buzonDeAlertas, BuzonDeClasificadores buzonDeClasificadores) {
        this.id = id;
        this.buzonDeAlertas = buzonDeAlertas;
        this.buzonDeClasificadores = buzonDeClasificadores;
        eventosRecibidos = new LinkedList<>();
    }

    public void leerEventos(){
        buzonDeAlertas.tomarEvento();

    }

    public void EnviarEvento(Evento evento) {
        if(esNormal()){
            while (buzonDeAlertas.estaLleno()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            buzonDeClasificadores.depositarEventoClasificado(evento);
        }
            else {
                while (buzonDeAlertas.estaLleno()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                buzonDeAlertas.decartarEvento(evento);
            }
       
    }

    public void recibirEventoFin(Evento eventoFin) {
            eventosRecibidos.add(eventoFin);

        }

        private boolean esNormal() {
            int numeroSeudoAleatorio =ThreadLocalRandom.current().nextInt(0, 20);
            return numeroSeudoAleatorio % 4 == 0;
        }

    @Override
    public void run() {
        while (eventosRecibidos.isEmpty() || !eventosRecibidos.peek().Esfin()) {
            leerEventos();
        }
    }
    
}
