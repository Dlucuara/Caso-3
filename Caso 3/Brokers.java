import java.util.concurrent.ThreadLocalRandom;

public class Brokers extends Thread {
    private int id; 
    private BuzonEntradaEventos buzonEntradaEventos;
    private final int totalEventos;
    private BuzonAdmin buzonAdmin;

    private BuzonDeAlertas buzonDeAlertas;
    private BuzonDeClasificadores buzonDeClasificadores;

    public Brokers(BuzonEntradaEventos buzonEntradaEventos , BuzonDeAlertas buzonDeAlertas, BuzonDeClasificadores buzonDeClasificadores, BuzonAdmin buzonAdmin) {
        this.totalEventos = Configuracion.ne * (Configuracion.ni * (Configuracion.ni + 1) / 2);
        this.buzonEntradaEventos = buzonEntradaEventos;
        this.buzonDeAlertas = buzonDeAlertas;
        this.buzonDeClasificadores = buzonDeClasificadores;
        this.buzonAdmin = buzonAdmin;
    }
    private Evento leerEvento() {
        return buzonEntradaEventos.tomarEvento();

    }

    

    public  void EnviarEvento(Evento evento) {
       if (esAnomalo()) {
        while (buzonDeAlertas.estaLleno()) {
            try {
                 Thread.yield();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
        }
            buzonDeAlertas.depositarEvento(evento);

        } else {
            while (buzonDeClasificadores.estaLleno()) {
                try {
                     Thread.yield();
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                }
            }
            buzonDeClasificadores.depositarEventoClasificado(evento);
        }
    }

    public boolean esAnomalo() {
        int numeroSeudoAleatorio =ThreadLocalRandom.current().nextInt(0, 200);
        return numeroSeudoAleatorio % 8 == 0;
    }

    @Override
    public void run() {
       int eventosProcessados = 0;
        Evento eventoFin = null;
        while (eventosProcessados < totalEventos) {
            Evento evento = leerEvento();  
            EnviarEvento(evento);       
            eventosProcessados++;
        }
        eventoFin = new Evento(id, -1);
        buzonAdmin.depositarEvento(eventoFin); 
        System.out.println("Broker " + id + " terminó.");
        
    }
}



